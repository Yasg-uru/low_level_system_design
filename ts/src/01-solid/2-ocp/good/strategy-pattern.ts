/**
 * GOOD: Open for extension, Closed for modification
 *
 * Use Strategy pattern to allow new payment methods
 * without modifying existing code.
 */

interface IPaymentStrategy {
  process(amount: number): Promise<boolean>;
  validate(amount: number): boolean;
}

class CreditCardPayment implements IPaymentStrategy {
  async process(amount: number): Promise<boolean> {
    if (!this.validate(amount)) return false;
    console.log(`Processing credit card payment: $${amount}`);
    return true;
  }

  validate(amount: number): boolean {
    return amount > 0 && amount <= 10000;
  }
}

class PayPalPayment implements IPaymentStrategy {
  async process(amount: number): Promise<boolean> {
    if (!this.validate(amount)) return false;
    console.log(`Processing PayPal payment: $${amount}`);
    return true;
  }

  validate(amount: number): boolean {
    return amount > 0 && amount <= 50000;
  }
}

class BankTransferPayment implements IPaymentStrategy {
  async process(amount: number): Promise<boolean> {
    if (!this.validate(amount)) return false;
    console.log(`Processing bank transfer: $${amount}`);
    return true;
  }

  validate(amount: number): boolean {
    return amount > 0 && amount <= 1000000;
  }
}

// NEW: Add cryptocurrency without modifying PaymentProcessor!
class CryptocurrencyPayment implements IPaymentStrategy {
  async process(amount: number): Promise<boolean> {
    if (!this.validate(amount)) return false;
    console.log(`Processing cryptocurrency payment: $${amount}`);
    return true;
  }

  validate(amount: number): boolean {
    return amount > 0 && amount <= 100000;
  }
}

class PaymentProcessor {
  async processPayment(amount: number, strategy: IPaymentStrategy): Promise<boolean> {
    return await strategy.process(amount);
  }
}

// Usage
const processor = new PaymentProcessor();

(async () => {
  const creditCard = new CreditCardPayment();
  const paypal = new PayPalPayment();
  const crypto = new CryptocurrencyPayment();

  await processor.processPayment(100, creditCard);
  await processor.processPayment(500, paypal);
  await processor.processPayment(1000, crypto);
})();
