/**
 * Singleton Pattern Example: Logger
 *
 * Practical example of singleton pattern usage.
 */

class Logger {
  private static instance: Logger | null = null;
  private logs: string[] = [];

  private constructor() {
    // Private constructor
  }

  static getInstance(): Logger {
    if (Logger.instance === null) {
      Logger.instance = new Logger();
      console.log('[Logger] Instance created');
    }
    return Logger.instance;
  }

  info(message: string): void {
    const log = `[INFO] ${new Date().toISOString()} - ${message}`;
    this.logs.push(log);
    console.log(log);
  }

  error(message: string, error?: Error): void {
    const log = `[ERROR] ${new Date().toISOString()} - ${message}`;
    if (error) {
      this.logs.push(`${log} | ${error.message}`);
    } else {
      this.logs.push(log);
    }
    console.error(log);
  }

  getLogs(): string[] {
    return [...this.logs];
  }
}

// Usage across application
const logger1 = Logger.getInstance();
logger1.info('Application started');

const logger2 = Logger.getInstance();
logger2.info('Database connected');

const logger3 = Logger.getInstance();
logger3.error('Something went wrong');

console.log('\n=== All Logs ===');
console.log(`Total logs: ${logger1.getLogs().length}`);
console.log(logger1 === logger2 && logger2 === logger3); // true - same instance
