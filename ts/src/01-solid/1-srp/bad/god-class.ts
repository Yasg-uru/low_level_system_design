/**
 * ANTI-PATTERN: God Class (Violates SRP)
 *
 * A single class doing too many unrelated things.
 * Hard to maintain, test, and understand.
 */

class UserManager {
  private users: Map<string, { email: string; password: string }> = new Map();

  // User management responsibility
  createUser(id: string, email: string, password: string): boolean {
    if (this.users.has(id)) {
      return false;
    }
    this.users.set(id, { email, password });
    return true;
  }

  deleteUser(id: string): boolean {
    return this.users.delete(id);
  }

  // Email sending responsibility
  sendWelcomeEmail(email: string): void {
    const mailHeader = 'From: noreply@app.com\n';
    const mailBody = `Welcome to our app!\n\nYour email: ${email}`;
    const mailFooter = '\n\nBest regards,\nTeam';
    const fullEmail = mailHeader + mailBody + mailFooter;
    console.log('Sending email:', fullEmail);
  }

  // Password hashing responsibility
  hashPassword(password: string): string {
    return btoa(password); // Not secure!
  }

  // Database backup responsibility
  backupDatabase(): void {
    const backupData = Array.from(this.users.entries());
    console.log('Backing up database...', JSON.stringify(backupData));
  }

  // Validation responsibility
  validateEmail(email: string): boolean {
    return email.includes('@');
  }

  validatePassword(password: string): boolean {
    return password.length >= 8;
  }

  // Authentication responsibility
  authenticate(id: string, password: string): boolean {
    const user = this.users.get(id);
    if (!user) return false;
    const hashedPassword = this.hashPassword(password);
    return user.password === hashedPassword;
  }

  // Logging responsibility
  logAction(action: string): void {
    console.log(`[${new Date().toISOString()}] ${action}`);
  }
}

// Problem: If we want to change email format, we modify UserManager
// Problem: If we want to change hash algorithm, we modify UserManager
// Problem: If we want to change validation rules, we modify UserManager
// This class has 7 reasons to change!
