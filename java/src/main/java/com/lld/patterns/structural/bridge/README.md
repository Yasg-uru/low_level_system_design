# Bridge Pattern

## Overview
The Bridge Pattern is a structural design pattern that decouples an abstraction from its implementation so that the two can vary independently. It accomplishes this by replacing class-level inheritance with object composition, allowing you to develop and extend the abstraction hierarchy and the implementation hierarchy in parallel without them being tightly coupled.

## When to Use
- When you want to avoid a permanent binding between an abstraction and its implementation.
- When both the abstractions and their implementations should be extensible by subclassing.
- When changes in the implementation of an abstraction should have no impact on clients.
- When you have a class explosion caused by a multi-dimensional class hierarchy (e.g., combining multiple types of notifications with multiple types of delivery channels).

## Benefits
- **Decoupling**: The implementation of an abstraction is not bound to the abstraction interface.
- **Improved Extensibility**: You can extend the abstraction (Notification types) and implementation (Communication channels) hierarchies independently.
- **Single Responsibility Principle**: High-level business rules (abstractions like standard, urgent notifications) and low-level details (implementations like email, SMS, push) are isolated.
- **Open/Closed Principle**: You can introduce new notification types and communication providers independently.

## Example Scenario
Consider a backend system that dispatches notifications. We have different kinds of notifications:
- **Notification Types (Abstraction)**: `NormalNotification`, `UrgentNotification`, `CriticalNotification`
- **Delivery Channels (Implementation)**: `EmailSender`, `SmsSender`, `PushSender`, `WhatsAppSender`

Without the Bridge pattern, if you try to support all notification types on all channels using inheritance, you face a class explosion ($N \times M$ subclasses):
- `NormalEmailNotification`, `UrgentEmailNotification`, `CriticalEmailNotification`
- `NormalSmsNotification`, `UrgentSmsNotification`, `CriticalSmsNotification`
- ...

By using the Bridge pattern, we separate the hierarchies. The `Notification` class maintains a reference to the `INotificationSender` interface, requiring only $N + M$ classes.

## Structure
```
                Abstraction                            Implementation
         +-----------------------+              +--------------------------+
         |     Notification      |------------->|   INotificationSender    |
         +-----------------------+              +--------------------------+
                     ^                                        ^
                     | (extends)                              | (implements)
         +-----------------------+              +--------------------------+
         | UrgentNotification    |              | EmailSender / SmsSender  |
         | CriticalNotification  |              | PushSender               |
         +-----------------------+              +--------------------------+
```

## Common Mistakes

❌ **Bad approach (Tight Coupling / Subclass Explosion)**
Using inheritance to combine the notification type and delivery channel, resulting in a large number of redundant subclasses.
```java
// Subclass explosion
public class UrgentEmailNotification extends EmailNotification {}
public class UrgentSmsNotification extends SmsNotification {}
public class UrgentPushNotification extends PushNotification {}
public class CriticalEmailNotification extends EmailNotification {}
public class CriticalSmsNotification extends SmsNotification {}
public class CriticalPushNotification extends PushNotification {}
```

✅ **Good approach (Bridge Pattern)**
Bridging the notification abstractions with a unified communication implementation interface.
```java
// Unified implementation interface
public interface INotificationSender {
    void send(String to, String content);
}

// Abstraction
public abstract class Notification {
    protected INotificationSender sender;
    
    protected Notification(INotificationSender sender) {
        this.sender = sender;
    }
    
    public abstract void send(String to, String content);
}

// Refined Abstraction
public class UrgentNotification extends Notification {
    public UrgentNotification(INotificationSender sender) {
        super(sender);
    }
    
    @Override
    public void send(String to, String content) {
        sender.send(to, "[URGENT] " + content);
    }
}
```

## Key Points
- The Abstraction defines the high-level business interface (e.g., standard, urgent, bulk notification logic).
- The Implementation defines the interface for low-level tasks (e.g., sending text to an email address or SMS number).
- The Abstraction delegates all actual communication tasks to the Implementation object.
- The Bridge makes it simple to switch notification channels at runtime.
