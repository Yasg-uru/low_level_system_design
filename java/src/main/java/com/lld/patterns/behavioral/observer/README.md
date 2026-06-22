# Observer Pattern

## Overview

The **Observer Pattern** is a behavioral design pattern that defines a one-to-many dependency between objects. When one object (the **Subject**) changes its state, all its registered dependents (the **Observers**) are notified and updated automatically.

This implementation decouples the order orchestration process from auxiliary services like notification dispatching, logging, auditing, and rewarding loyalty points, notifying them asynchronously using Java's `CompletableFuture`.

## When to Use

- When changes to the state of one object require changing other objects, and the set of objects needing changes is dynamic or unknown beforehand.
- When an object needs to notify others without making assumptions about their concrete classes (loose coupling).
- When a service needs to execute side effects (e.g., sending an email, logging) triggered by a primary business event (e.g., placing an order) without blocking or intertwining the core business logic.

## Benefits

- **Loose Coupling**: The Subject depends only on the `IOrderObserver` interface, not on concrete implementations.
- **Open/Closed Principle**: You can introduce new observers (e.g., inventory updater, shipping manager) without modifying the Subject or existing observers.
- **Asynchronous Execution**: Using `CompletableFuture` allows observers to process updates asynchronously, keeping the main flow responsive.

## Example Scenario

In an e-commerce platform, when an order is placed (`ORDER_PLACED`) or shipped (`ORDER_SHIPPED`):
- **Subject**: `OrderEventEmitter`
- **Observers**:
  - `NotificationService`: Sends SMS/Emails to customers.
  - `LoggingService`: Logs actions for technical diagnostic purposes.
  - `AuditService`: Audits order financial details for compliance.
  - `LoyaltyPointAwarder`: Awards points on successful purchases.

## Structure

```
                  +-------------------------+
                  |   IOrderEventEmitter    |
                  +-------------------------+
                               |
                               | (manages & notifies)
                               v
                  +-------------------------+
                  |     IOrderObserver      |
                  +-------------------------+
                               ^
                               |
       +-----------------------+-----------------------+
       |                       |                       |
+-------------------+   +-------------------+   +--------------------+
|  LoggingService   |   |NotificationService|   |LoyaltyPointAwarder |
+-------------------+   +-------------------+   +--------------------+
```

## Common Mistakes

❌ **Bad approach (Tight Coupling / Violation of Single Responsibility)**
Directly invoking auxiliary helper classes inside the order placing flow. This makes it impossible to add/remove actions without rewriting the core `OrderService`.
```java
public class OrderService {
    private EmailService emailService = new EmailService();
    private LoggingService loggingService = new LoggingService();

    public void placeOrder(Order order) {
        // Business logic...
        
        emailService.sendOrderEmail(order);
        loggingService.log(order);
        // Any new action requires changing this method
    }
}
```

✅ **Good approach (Observer Pattern)**
Decoupling the execution using an event emitter and observer interfaces.
```java
public interface IOrderObserver {
    CompletableFuture<Void> update(OrderEvent event);
}

public class OrderEventEmitter implements IOrderEventEmitter {
    private final List<IOrderObserver> observers = new ArrayList<>();
    
    public void attach(IOrderObserver o) { observers.add(o); }
    
    public CompletableFuture<Void> notify(OrderEvent event) {
        CompletableFuture<Void> chain = CompletableFuture.completedFuture(null);
        for (IOrderObserver observer : observers) {
            chain = chain.thenCompose(v -> observer.update(event));
        }
        return chain;
    }
}
```

## Key Points

- The Subject provides clear methods to `attach()` and `detach()` observers dynamically at runtime.
- In Java, using `CompletableFuture` for observer updates ensures that the notifications can run asynchronously without blocking the core application flow.
- Observers decide internally whether to act on a specific event type (e.g., `LoyaltyPointAwarder` only processes `ORDER_PLACED` events).
