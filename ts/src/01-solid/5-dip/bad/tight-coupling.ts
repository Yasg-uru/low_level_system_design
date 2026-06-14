/**
 * ANTI-PATTERN: Tight Coupling (Violates DIP)
 *
 * High-level modules depend on low-level modules.
 * Hard to test and change implementations.
 */

class MySQLDatabase {
  connect(): void {
    console.log('Connecting to MySQL...');
  }

  save(data: string): void {
    console.log(`Saving to MySQL: ${data}`);
  }
}

class UserService {
  private database: MySQLDatabase;

  constructor() {
    this.database = new MySQLDatabase(); // Direct dependency!
  }

  createUser(name: string): void {
    this.database.connect();
    this.database.save(`User: ${name}`);
  }
}

// PROBLEM: UserService depends on MySQLDatabase
// PROBLEM: Can't swap MySQL for PostgreSQL without modifying UserService
// PROBLEM: Hard to test UserService (can't mock database)
// PROBLEM: If MySQLDatabase changes, UserService breaks
