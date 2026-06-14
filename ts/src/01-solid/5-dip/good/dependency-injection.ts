/**
 * GOOD: Dependency Inversion (Following DIP)
 *
 * High-level modules depend on abstractions.
 * Easy to test and swap implementations.
 */

interface IDatabase {
  connect(): void;
  save(data: string): void;
}

class MySQLDatabase implements IDatabase {
  connect(): void {
    console.log('Connecting to MySQL...');
  }

  save(data: string): void {
    console.log(`Saving to MySQL: ${data}`);
  }
}

class PostgreSQLDatabase implements IDatabase {
  connect(): void {
    console.log('Connecting to PostgreSQL...');
  }

  save(data: string): void {
    console.log(`Saving to PostgreSQL: ${data}`);
  }
}

class MockDatabase implements IDatabase {
  connect(): void {
    console.log('[MOCK] Connect');
  }

  save(data: string): void {
    console.log(`[MOCK] Save: ${data}`);
  }
}

class UserService {
  constructor(private database: IDatabase) {}

  createUser(name: string): void {
    this.database.connect();
    this.database.save(`User: ${name}`);
  }
}

// Usage: Can inject any database implementation
const mysqlDb = new MySQLDatabase();
const postgresDb = new PostgreSQLDatabase();
const mockDb = new MockDatabase();

const service1 = new UserService(mysqlDb);
const service2 = new UserService(postgresDb);
const service3 = new UserService(mockDb); // Perfect for testing!

service1.createUser('Alice');
service2.createUser('Bob');
service3.createUser('Charlie');
