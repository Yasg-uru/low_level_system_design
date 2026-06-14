package com.lld.solid.srp.good;

/**
 * Orchestrates user creation using separate responsibilities.
 * Each class has one reason to change.
 */
public class UserCreationService {
    private IUserRepository userRepository;
    private IPasswordHasher passwordHasher;
    private IValidator emailValidator;
    private IValidator passwordValidator;
    private IEmailService emailService;
    private ILogger logger;

    public UserCreationService(
            IUserRepository userRepository,
            IPasswordHasher passwordHasher,
            IValidator emailValidator,
            IValidator passwordValidator,
            IEmailService emailService,
            ILogger logger) {
        this.userRepository = userRepository;
        this.passwordHasher = passwordHasher;
        this.emailValidator = emailValidator;
        this.passwordValidator = passwordValidator;
        this.emailService = emailService;
        this.logger = logger;
    }

    public boolean createUser(String id, String email, String password) {
        if (!emailValidator.validate(email)) {
            logger.log("Invalid email: " + email);
            return false;
        }

        if (!passwordValidator.validate(password)) {
            logger.log("Invalid password for user " + id);
            return false;
        }

        String hashedPassword = passwordHasher.hash(password);
        User user = new User(id, email, hashedPassword);
        userRepository.add(user);

        emailService.send(email, "Welcome", "Welcome to our app!");
        logger.log("User created: " + id);
        return true;
    }
}
