/**
 * GOOD: Following Single Responsibility Principle
 *
 * Each class has one reason to change.
 * Easy to test, maintain, and extend.
 */

interface IPasswordHasher {
  hash(password: string): string;
  verify(password: string, hash: string): boolean;
}

interface IEmailService {
  send(email: string, subject: string, body: string): void;
}

interface IValidator {
  validate(value: string): boolean;
}

// Responsibility: Hash passwords
class BCryptPasswordHasher implements IPasswordHasher {
  hash(password: string): string {
    return `hashed_${password}`;
  }

  verify(password: string, hash: string): boolean {
    return this.hash(password) === hash;
  }
}

// Responsibility: Validate emails
class EmailValidator implements IValidator {
  validate(email: string): boolean {
    return /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email);
  }
}

// Responsibility: Validate passwords
class PasswordValidator implements IValidator {
  validate(password: string): boolean {
    return password.length >= 8;
  }
}

// Responsibility: Send emails
class EmailService implements IEmailService {
  send(email: string, subject: string, body: string): void {
    const message = `To: ${email}\nSubject: ${subject}\n\n${body}`;
    console.log('Sending email:\n', message);
  }
}

// Responsibility: Log events
class Logger {
  log(message: string): void {
    console.log(`[${new Date().toISOString()}] ${message}`);
  }
}

// Responsibility: ONLY manage users
class User {
  constructor(
    readonly id: string,
    readonly email: string,
    private passwordHash: string
  ) {}

  verifyPassword(password: string, hasher: IPasswordHasher): boolean {
    return hasher.verify(password, this.passwordHash);
  }
}

class UserRepository {
  private users: Map<string, User> = new Map();

  add(user: User): void {
    this.users.set(user.id, user);
  }

  get(id: string): User | undefined {
    return this.users.get(id);
  }

  delete(id: string): boolean {
    return this.users.delete(id);
  }
}

// Responsibility: Orchestrate user creation flow
class UserCreationService {
  constructor(
    private userRepository: UserRepository,
    private passwordHasher: IPasswordHasher,
    private emailValidator: IValidator,
    private passwordValidator: IValidator,
    private emailService: IEmailService,
    private logger: Logger
  ) {}

  createUser(id: string, email: string, password: string): boolean {
    if (!this.emailValidator.validate(email)) {
      this.logger.log(`Invalid email: ${email}`);
      return false;
    }

    if (!this.passwordValidator.validate(password)) {
      this.logger.log(`Invalid password for user ${id}`);
      return false;
    }

    const hashedPassword = this.passwordHasher.hash(password);
    const user = new User(id, email, hashedPassword);
    this.userRepository.add(user);

    this.emailService.send(
      email,
      'Welcome',
      'Welcome to our app!\n\nYour account is ready to use.'
    );

    this.logger.log(`User created: ${id}`);
    return true;
  }
}

// Usage: Clean, focused responsibilities
const passwordHasher = new BCryptPasswordHasher();
const emailValidator = new EmailValidator();
const passwordValidator = new PasswordValidator();
const emailService = new EmailService();
const logger = new Logger();
const userRepository = new UserRepository();

const userCreationService = new UserCreationService(
  userRepository,
  passwordHasher,
  emailValidator,
  passwordValidator,
  emailService,
  logger
);

userCreationService.createUser('user1', 'user@example.com', 'securePass123');
