# Prototype Design Pattern (Java)

## What is it?

The **Prototype Design Pattern** is a creational design pattern that allows cloning existing objects without coupling your code to their specific concrete classes. Instead of creating a new instance from scratch using a constructor (which might involve expensive operations like fetching database records or reading config files), you clone an existing archetypal instance (the prototype).

## Why use it?

- **Reduced Construction Cost**: Avoids the performance overhead of complex initialization logic (e.g., parsing HTML templates, reading file attachments) by copying pre-instantiated memory blocks.
- **Decoupled Client Code**: Clients do not need to know the concrete configuration classes or complex constructor arguments; they simply request a copy of a baseline prototype and customize it.
- **Simplifies Object State Combinations**: Rather than maintaining a complex hierarchy of subclasses representing various state configurations, you can store pre-configured instances in a registry and clone them.

## When to use?

- When object creation is computationally expensive or resource-intensive.
- When you want to isolate the client from the concrete implementation details of the object being copied.
- When your system needs to produce objects that only differ slightly in state (e.g., email templates with different client names, game entities with varied stats).

## Common Mistakes

❌ **Bad approach (Shallow copy / default Java `clone()`)**
```java
// Java's default Object.clone() performs a shallow copy by default!
// If your object contains references to mutable objects (like Lists or Maps), both the original and copy will share them.
public class EmailTemplate implements Cloneable {
    private List<EmailAttachment> attachments;

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone(); // WARNING: attachments list is shared between copy and original!
    }
}
```

✅ **Good approach (Explicit deep copying via custom interface / copy constructor)**
```java
public interface IPrototype<T> {
    T clonePrototype();
}

public class EmailTemplate implements IPrototype<EmailTemplate> {
    private List<EmailAttachment> attachments;

    // Copy Constructor style deep copy
    private EmailTemplate(EmailTemplate other) {
        if (other.attachments != null) {
            this.attachments = new ArrayList<>();
            for (EmailAttachment attachment : other.attachments) {
                this.attachments.add(attachment.cloneAttachment()); // Deep copies attachment buffers
            }
        }
    }

    @Override
    public EmailTemplate clonePrototype() {
        return new EmailTemplate(this);
    }
}
```

## Java Features Used

- **Generics**: The registry `PrototypeRegistry<T extends IPrototype<T>>` uses generics to enforce type-safety on prototypes.
- **Copy Constructors**: Utilized within `EmailTemplate` to safely copy primitive, object, and collection references cleanly without the caveats of `java.lang.Cloneable`.
- **String/Buffer Operations**: Byte array copying (`Arrays.copyOf`) to clone file attachment data.

## Files in This Package

- `IPrototype.java`: General prototype contract interface.
- `User.java`: User entity representing recipients.
- `EmailAttachment.java`: Encapsulates attachments with deep-cloning capability.
- `EmailTemplate.java`: Concrete prototype for email templates.
- `PrototypeRegistry.java`: Central registry mapping template blueprints to cached prototypes.
- `IMailer.java`: Abstract mail delivery service interface.
- `SMTPMailer.java`: Simulated SMTP client.
- `EmailService.java`: Client service consuming prototype templates.
- `Main.java`: Execution driver.
