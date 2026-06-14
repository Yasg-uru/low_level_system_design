/**
 * Abstraction using Interfaces
 *
 * Interfaces define contracts that classes must fulfill.
 * They provide pure abstraction (no implementation).
 */

interface IPaymentProcessor {
  processPayment(amount: number): Promise<boolean>;
  refund(transactionId: string): Promise<boolean>;
  getTransactionStatus(transactionId: string): string;
}

interface ILogger {
  log(message: string): void;
  error(message: string, error?: Error): void;
}

class StripePayment implements IPaymentProcessor {
  private logger: ILogger;

  constructor(logger: ILogger) {
    this.logger = logger;
  }

  async processPayment(amount: number): Promise<boolean> {
    try {
      this.logger.log(`Processing Stripe payment: $${amount}`);
      // Simulate API call
      await new Promise((resolve) => setTimeout(resolve, 100));
      this.logger.log('Stripe payment successful');
      return true;
    } catch (error) {
      this.logger.error('Stripe payment failed', error as Error);
      return false;
    }
  }

  async refund(transactionId: string): Promise<boolean> {
    this.logger.log(`Refunding Stripe transaction: ${transactionId}`);
    return true;
  }

  getTransactionStatus(transactionId: string): string {
    return `Stripe transaction ${transactionId}: completed`;
  }
}

class PayPalPayment implements IPaymentProcessor {
  private logger: ILogger;

  constructor(logger: ILogger) {
    this.logger = logger;
  }

  async processPayment(amount: number): Promise<boolean> {
    try {
      this.logger.log(`Processing PayPal payment: $${amount}`);
      await new Promise((resolve) => setTimeout(resolve, 150));
      this.logger.log('PayPal payment successful');
      return true;
    } catch (error) {
      this.logger.error('PayPal payment failed', error as Error);
      return false;
    }
  }

  async refund(transactionId: string): Promise<boolean> {
    this.logger.log(`Refunding PayPal transaction: ${transactionId}`);
    return true;
  }

  getTransactionStatus(transactionId: string): string {
    return `PayPal transaction ${transactionId}: pending`;
  }
}

class ConsoleLogger implements ILogger {
  log(message: string): void {
    console.log(`[INFO] ${message}`);
  }

  error(message: string, error?: Error): void {
    console.error(`[ERROR] ${message}`, error?.message);
  }
}

// Usage
const logger = new ConsoleLogger();
const processor: IPaymentProcessor = new StripePayment(logger);

(async () => {
  await processor.processPayment(99.99);
  console.log(processor.getTransactionStatus('txn_123'));
})();
