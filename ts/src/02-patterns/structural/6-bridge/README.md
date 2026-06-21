# Bridge Pattern

## Overview
The Bridge Pattern is a structural design pattern that decouples an abstraction from its implementation so that the two can vary independently. It accomplishes this by replacing inheritance with object composition, allowing you to develop and extend the abstraction hierarchy and the implementation hierarchy in parallel without them being tightly coupled.

## When to Use
- When you want to avoid a permanent binding between an abstraction and its implementation.
- When both the abstractions and their implementations should be extensible by subclassing.
- When changes in the implementation of an abstraction should have no impact on clients.
- When you have a class explosion caused by a multi-dimensional class hierarchy (e.g., combining multiple types of notifications with multiple types of communication channels).

## Benefits
- **Decoupling**: The implementation of an abstraction is not bound to the abstraction interface.
- **Improved Extensibility**: You can extend the abstraction (Notification types) and implementation (Communication channels) hierarchies independently.
- **Single Responsibility Principle**: You can focus on high-level business logic (e.g., standard, urgent, bulk notifications) in the abstraction, and low-level provider details (e.g., email, SMS, push) in the implementations.
- **Open/Closed Principle**: You can introduce new notification types and communication providers independently without breaking existing code.

## Example Scenario
Consider a backend system that dispatches notifications. We have different kinds of notifications:
- **Notification Types (Abstraction)**: `Standard`, `Urgent`, `Bulk`
- **Delivery Channels (Implementation)**: `Email`, `SMS`, `Push Notification`

Without the Bridge pattern, if you try to support all types on all channels using inheritance, you face a class explosion ($N \times M$ subclasses):
- `StandardEmailNotification`, `UrgentEmailNotification`, `BulkEmailNotification`
- `StandardSmsNotification`, `UrgentSmsNotification`, `BulkSmsNotification`
- `StandardPushNotification`, `UrgentPushNotification`, `BulkPushNotification`

By using the Bridge pattern, we separate the hierarchies. The `NotificationService` maintains a reference to the `INotificationSender` interface, requiring only $N + M$ classes. Adding a new channel (like `WhatsApp`) or a new notification type (like `ScheduledNotification`) requires only 1 class instead of multiplying the total count.

## Structure
```
                Abstraction                            Implementation
         +-----------------------+              +--------------------------+
         |  NotificationService  |------------->|   INotificationSender    |
         +-----------------------+              +--------------------------+
                     ^                                        ^
                     | (extends)                              | (implements)
         +-----------------------+              +--------------------------+
         | UrgentNotification    |              | EmailSender / SmsSender  |
         | BulkNotification      |              | PushSender               |
         +-----------------------+              +--------------------------+
```

## Common Mistakes

❌ **Bad approach (Tight Coupling / Subclass Explosion)**
Using inheritance to combine the notification type and delivery channel, resulting in a large number of redundant subclasses.
```typescript
// Subclass explosion
class UrgentEmailNotification {}
class UrgentSmsNotification {}
class UrgentPushNotification {}
class BulkEmailNotification {}
class BulkSmsNotification {}
class BulkPushNotification {}
```

✅ **Good approach (Bridge Pattern)**
Bridging the notification abstractions with a unified communication implementation interface.
```typescript
// Unified implementation interface
interface INotificationSender {
  send(recipient: string, content: string): void;
}

// Abstraction
class NotificationService {
  constructor(protected sender: INotificationSender) {}
  
  sendNotification(recipient: string, message: string): void {
    this.sender.send(recipient, message);
  }
}

// Refined Abstraction
class UrgentNotificationService extends NotificationService {
  override sendNotification(recipient: string, message: string): void {
    this.sender.send(recipient, `[URGENT] ${message}`);
  }
}
```

## Key Points
- The Abstraction defines the high-level business interface (e.g., standard, urgent, bulk notification logic).
- The Implementation defines the interface for low-level tasks (e.g., sending text to an email address or SMS number).
- The Abstraction delegates all actual communication tasks to the Implementation object.
- The Bridge makes it simple to switch notification channels at runtime.

## Related Concepts
- **[Adapter Pattern](../1-adapter/)**: Adapter is usually applied to an existing system to make unrelated classes work together. Bridge is used up-front in design to let abstractions and implementations vary independently.
- **[Abstract Factory Pattern](../../creational/3-abstract-factory/)**: Can be used to create and configure a specific Bridge.
