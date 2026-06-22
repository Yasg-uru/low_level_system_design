# Strategy Design Pattern

## What is it?

The **Strategy Pattern** is a behavioral design pattern that defines a family of algorithms, encapsulates each one, and makes them interchangeable. Strategy lets the algorithm vary independently from clients that use it.

By using composition instead of inheritance, it allows an object (the **Context**) to delegate its behavior or algorithm to a separate strategy object, which can be swapped at runtime.

## Why use it?

- **Open/Closed Principle**: You can introduce new strategies (algorithms) without having to change the context or existing strategies.
- **Avoid Conditional Statements**: It replaces massive switch or conditional statements (`if-else`) used to select behaviors.
- **Interchangeable Behaviors**: Strategies can be swapped dynamically at runtime depending on user input or state.
- **Isolation of Complexity**: You can isolate the implementation details of complex algorithms from the core business logic.

## When to use?

- When you have many variations of an algorithm/behavior, and you need to switch between them dynamically.
- When you want to isolate the business logic of a class from the implementation details of its algorithms.
- When a class defines many behaviors, and these appear as multiple conditional statements in its operations.

## Common Mistakes

❌ **Bad approach (Hardcoded Conditional Logic / Bad Extensibility)**
If a new payment type is added, the `PaymentService` class has to be modified, violating the Open/Closed Principle.
```typescript
class PaymentService {
  processPayment(method: string, amount: number) {
    if (method === "credit_card") {
      console.log(`Processing credit card payment of $${amount}`);
    } else if (method === "paypal") {
      console.log(`Processing paypal payment of $${amount}`);
    } else if (method === "bitcoin") {
      console.log(`Processing bitcoin payment of $${amount}`);
    } else {
      throw new Error("Unsupported payment method");
    }
  }
}
```

✅ **Good approach (Strategy Pattern)**
Define an interface for the strategies and let the context execute the selected strategy.
```typescript
interface IPaymentStrategy {
  processPayment(amount: number): void;
}

class CreditCardPaymentStrategy implements IPaymentStrategy {
  processPayment(amount: number) {
    console.log(`Processing credit card payment of $${amount}`);
  }
}

class PaymentContext {
  private strategy: IPaymentStrategy;

  constructor(strategy: IPaymentStrategy) {
    this.strategy = strategy;
  }

  setStrategy(strategy: IPaymentStrategy) {
    this.strategy = strategy;
  }

  pay(amount: number) {
    this.strategy.processPayment(amount);
  }
}
```

## Key Points

- **Context**: Maintains a reference to a Strategy object and communicates with it only via the Strategy interface.
- **Strategy Interface**: Declares a method common to all supported algorithms.
- **Concrete Strategies**: Implement the algorithm defined in the Strategy Interface.

## Related Concepts

- **State Pattern**: The State pattern is closely related. The key difference is that in the State pattern, the states are aware of each other and transition from one to another, whereas Strategies are usually independent and do not know about other strategies.
- **Factory Pattern**: A Factory pattern is frequently used to instantiate the appropriate Strategy based on user input or configuration.
