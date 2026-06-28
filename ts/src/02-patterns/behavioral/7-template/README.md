# Template Method Design Pattern

## What is it?

The **Template Method Pattern** is a behavioral design pattern that defines the skeleton of an algorithm in an abstract base class, deferring some of the individual steps to subclasses. This allows subclasses to redefine certain steps of the algorithm without changing the overall structure of the algorithm itself.

It is particularly useful for establishing a strict workflow sequence where some steps are invariant (identical across all implementations) and others are variant (must be customized by subclasses).

## Why use it?

- **Eliminates Code Duplication**: Standardizes the shared steps of an algorithm (such as validation, error handling, or logging) in a single superclass.
- **Controls Customization Points**: Subclasses can only override the specific steps defined as abstract or hook methods, preserving the integrity of the overall sequence.
- **Open/Closed Principle**: You can introduce new workflow configurations (subclasses) without altering the base class or client code.

## When to use?

- When you have a multi-step algorithm with an invariant sequential skeleton, and only some steps differ.
- When you want to control extension points—allowing subclasses to extend or customize only specific parts of a workflow.
- When multiple classes contain nearly identical algorithms with minor differences in certain steps.

## Common Mistakes

❌ **Bad approach (Code Duplication across Classes)**
Repeating the workflow sequence and boilerplate steps (like validation and logging) inside every registration service creates duplicate maintenance points. If the validation rules change, every service must be modified.
```typescript
class CustomerRegistration {
  async register(userData: User) {
    // Repeated validation logic
    if (!userData.username || !userData.email) throw new Error("Validation failed");
    
    // Custom database creation
    console.log("Create customer DB record...");
    
    // Custom notification
    console.log("Send customer welcome email...");
    
    // Repeated logging
    console.log(`User registered: ${userData.username}`);
  }
}

class AdminRegistration {
  async register(userData: User) {
    // Repeated validation logic (identical)
    if (!userData.username || !userData.email) throw new Error("Validation failed");
    
    // Custom database creation
    console.log("Create secure admin record...");
    
    // Custom notification
    console.log("Send security alert notification...");
    
    // Repeated logging (identical)
    console.log(`User registered: ${userData.username}`);
  }
}
```

✅ **Good approach (Template Method Pattern)**
By encapsulating the sequence structure inside an abstract base template, subclasses are only responsible for implementing their custom operations (`createUser` and `sendWelcomeEmail`).
```typescript
abstract class UserRegistrationTemplate {
  // Invariant sequential skeleton
  public async registerUser(userData: User): Promise<User> {
    this.validate(userData);
    const user = await this.createUser(userData);
    await this.sendWelcomeEmail(user);
    this.log(user);
    return user;
  }

  protected validate(userData: User) {
    if (!userData.username || !userData.email) throw new Error("Validation failed");
  }

  // Primitive operations to be overridden by subclasses
  protected abstract createUser(userData: User): Promise<User>;
  protected abstract sendWelcomeEmail(user: User): Promise<void>;

  protected log(user: User) {
    console.log(`User registered: ${user.username}`);
  }
}
```

## Key Points

- **Template Method**: The public method in the base class (`registerUser`) that outlines the exact steps and their execution order. It should generally not be overridden by subclasses.
- **Primitive Operations**: Abstract methods declared in the base class that concrete subclasses *must* implement to fulfill the algorithm.
- **Hook Methods**: Concrete methods with empty or default behavior in the base class (`logRegistration`) that subclasses *can* optionally override to inject behavior at specific moments.

## Related Concepts

- **[Strategy Pattern](../2-strategy/)**: Strategy modifies the entire algorithm dynamically using composition, while Template Method modifies parts of an algorithm statically using inheritance.
- **[Factory Method](../../creational/2-factory-method/)**: Template methods frequently call factory methods as part of their steps to instantiate objects.
