# Observer Design Pattern

## What is it?

The **Observer Pattern** is a behavioral design pattern that defines a one-to-many dependency between objects. When one object (the **Subject** or **Emitter**) changes its state, all its registered dependents (the **Observers** or **Listeners**) are notified and updated automatically.

It promotes loose coupling by allowing the subject to notify a dynamic list of observers without knowing their concrete implementations.

## Why use it?

- **Loose Coupling**: The subject only knows that the observers implement a specific interface. It doesn't need to know their concrete classes or internal workings.
- **Support for Broadcast Communication**: Unlike typical function calls, notifications are broadcast automatically to all interested parties.
- **Dynamic Relationships**: Observers can be attached or detached at runtime.
- **Clean Architecture / SRP**: You can separate core business flows (e.g., placing an order) from secondary flows (e.g., sending emails, writing logs, updating analytics) by delegating them to independent observers.

## When to use?

- When an abstraction has two aspects, one dependent on the other. Encapsulating these aspects in separate objects lets you vary and reuse them independently.
- When a change to one object requires changing others, and you don't know how many objects need to change.
- When an object should be able to notify other objects without making assumptions about who these objects are.

## Common Mistakes

❌ **Bad approach (Hardcoded / Tight Coupling)**
```typescript
class OrderService {
  private emailService = new EmailService();
  private smsService = new SMSService();
  private auditLogger = new AuditLogger();

  placeOrder(order: Order) {
    // Core logic
    console.log("Order placed");

    // Hardcoded side-effects (violates Open/Closed Principle)
    this.emailService.sendEmail(order);
    this.smsService.sendSMS(order);
    this.auditLogger.logOrder(order);
  }
}
```

✅ **Good approach (Observer Pattern)**
```typescript
interface IOrderObserver {
  update(order: Order): void;
}

class OrderService {
  private observers: IOrderObserver[] = [];

  attach(observer: IOrderObserver) {
    this.observers.push(observer);
  }

  placeOrder(order: Order) {
    console.log("Order placed");
    this.notifyAll(order);
  }

  private notifyAll(order: Order) {
    for (const observer of this.observers) {
      observer.update(order);
    }
  }
}
```

## Key Points

- **Subject / Emitter**: Maintains a list of observers and provides interfaces to attach/detach them.
- **Observer**: Defines the updating interface for objects that should be notified of changes.
- **Push vs. Pull Model**:
  - **Push**: The subject sends detailed information about the change (e.g., an event object).
  - **Pull**: The subject sends minimal notification, and observers request the details they need.

## Related Concepts

- **Mediator**: Mediator centralizes communication between colleague objects, whereas Observer distributes notification to multiple observers.
- **Publish-Subscribe (Pub/Sub)**: Pub/Sub is a sibling pattern that uses an event broker/channel between publishers and subscribers, decoupling them completely (they don't even know about each other's existence, unlike Observer where the subject maintains a direct list of observers).
