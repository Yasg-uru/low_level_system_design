/**
 * TEMPLATE METHOD DESIGN PATTERN EXAMPLE
 * * Intent: Defines the skeleton of an algorithm in a method, deferring some steps 
 * to subclasses. Template Method lets subclasses redefine certain steps of an 
 * algorithm without changing its structure.
 */

// --- 1. CORE DOMAIN INTERFACE ---
interface User {
  username: string;
  email: string;
  password: string;
}

// --- 2. THE ABSTRACT TEMPLATE CLASS ---
abstract class UserRegistrationTemplate {
  // The Template Method: defines the invariant sequential skeleton of the workflow
  public async registerUser(userData: User): Promise<User> {
    this.validateUserData(userData);
    const user = await this.createUser(userData);
    await this.sendWelcomeEmail(user);
    this.logRegistration(user);
    return user;
  }

  // Common/Default implementation used by all subclasses
  protected validateUserData(userData: User): void {
    if (!userData.username || !userData.email || !userData.password) {
      throw new Error("All fields are required");
    }
    if (!this.isValidEmail(userData.email)) {
      throw new Error("Invalid email format");
    }
    if (!this.isStrongPassword(userData.password)) {
      throw new Error("Password must be at least 8 characters long and contain a mix of letters and numbers");
    }
  }

  // Primitive operations: These MUST be customized by concrete subclasses
  protected abstract createUser(userData: User): Promise<User>;
  protected abstract sendWelcomeEmail(user: User): Promise<void>;

  // A hook method or shared utility step
  protected logRegistration(user: User): void {
    console.log(`[Log] User registered: ${user.username} (${user.email})`);
  }

  private isValidEmail(email: string): boolean {
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return emailRegex.test(email);
  }

  private isStrongPassword(password: string): boolean {
    return password.length >= 8 && /[A-Za-z]/.test(password) && /[0-9]/.test(password);
  }
}

// --- 3. CONCRETE IMPLEMENTATIONS ---

// Subclass A: Standard Customer Configuration
class CustomerUserRegistration extends UserRegistrationTemplate {
  protected async createUser(userData: User): Promise<User> {
    return new Promise((resolve) => {
      setTimeout(() => {
        console.log(`[DB] Saved customer row for ${userData.username}`);
        resolve(userData);
      }, 100);
    });
  }

  protected async sendWelcomeEmail(user: User): Promise<void> {
    return new Promise((resolve) => {
      setTimeout(() => {
        console.log(`[Mail] Automated customer onboarding welcome sent to ${user.email}`);
        resolve();
      }, 50);
    });
  }
}

// Subclass B: Elevated Admin Configuration
class AdminUserRegistration extends UserRegistrationTemplate {
  protected async createUser(userData: User): Promise<User> {
    return new Promise((resolve) => {
      setTimeout(() => {
        console.log(`[DB] Created secure system administrative record for ${userData.username}`);
        resolve(userData);
      }, 100);
    });
  }

  protected async sendWelcomeEmail(user: User): Promise<void> {
    return new Promise((resolve) => {
      setTimeout(() => {
        console.log(`🚨 SECURITY ALERT: High-level administrative entry created for ${user.email}`);
        resolve();
      }, 50);
    });
  }
}

// --- 4. DEMONSTRATION RUN ---

async function runTemplateMethodDemo(): Promise<void> {
  console.log("--- Executing Customer Workflow Pipeline ---");
  const customerRegistration = new CustomerUserRegistration();
  await customerRegistration.registerUser({ 
    username: "john_doe", 
    email: "john.doe@example.com", 
    password: "SecurePass123" 
  });

  console.log("\n--- Executing Admin Workflow Pipeline ---");
  const adminRegistration = new AdminUserRegistration();
  await adminRegistration.registerUser({ 
    username: "root_admin", 
    email: "admin@company.com", 
    password: "AdminPass123" 
  });
}

runTemplateMethodDemo();

export {};