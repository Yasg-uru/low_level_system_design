/**
 * STRATEGY DESIGN PATTERN EXAMPLE
 * * Intent: Define a family of algorithms, encapsulate each one, and make them interchangeable. 
 * Strategy lets the algorithm vary independently from clients that use it.
 */

// --- 1. THE STRATEGY INTERFACE ---

interface IPaymentStrategy {
  processPayment(amount: number): void;
}

// --- 2. CONCRETE STRATEGIES ---

class CreditCardPaymentStrategy implements IPaymentStrategy {
  processPayment(amount: number): void {
    console.log(`Processing Credit Card payment of $${amount.toFixed(2)}`);
  }
}

class PayPalPaymentStrategy implements IPaymentStrategy {
  processPayment(amount: number): void {
    console.log(`Processing PayPal payment of $${amount.toFixed(2)}`);
  }
}

class BitcoinPaymentStrategy implements IPaymentStrategy {
  processPayment(amount: number): void {
    console.log(`Processing Bitcoin payment of $${amount.toFixed(2)}`);
  }
}

// --- 3. THE CONTEXT ---

class PaymentContext {
  private strategy: IPaymentStrategy;

  // The context accepts a strategy through the constructor
  constructor(strategy: IPaymentStrategy) {
    this.strategy = strategy;
  }

  // Allows switching strategies dynamically at runtime
  setStrategy(strategy: IPaymentStrategy): void {
    this.strategy = strategy;
  }

  // Delegates the heavy lifting task to the current Strategy object
  pay(amount: number): void {
    this.strategy.processPayment(amount);
  }
}

// --- 4. DEMONSTRATION RUN ---

function runStrategyDemo(): void {
  console.log("--- Starting Strategy Pattern Demo ---");

  // Initialized with Credit Card
  const paymentContext = new PaymentContext(new CreditCardPaymentStrategy());
  paymentContext.pay(100.00);

  // Dynamically swap strategy to PayPal
  paymentContext.setStrategy(new PayPalPaymentStrategy());
  paymentContext.pay(200.00);

  // Dynamically swap strategy to Bitcoin
  paymentContext.setStrategy(new BitcoinPaymentStrategy());
  paymentContext.pay(300.00);
}

runStrategyDemo();