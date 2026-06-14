/**
 * Singleton Pattern: Basic Implementation
 *
 * Ensures only one instance of a class exists throughout the application.
 * Used for: loggers, database connections, configuration managers.
 */

class Singleton {
  private static instance: Singleton | null = null;

  private constructor() {
    // Private constructor prevents direct instantiation
  }

  static getInstance(): Singleton {
    if (Singleton.instance === null) {
      Singleton.instance = new Singleton();
    }
    return Singleton.instance;
  }

  doSomething(): string {
    return 'Doing something...';
  }
}

// Usage
const instance1 = Singleton.getInstance();
const instance2 = Singleton.getInstance();

console.log(instance1 === instance2); // true - same instance
console.log(instance1.doSomething());
