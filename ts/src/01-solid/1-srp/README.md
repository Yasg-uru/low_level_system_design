# Single Responsibility Principle (SRP)

## What is it?

A class should have only one reason to change. It should have only one responsibility or job. This means a class should do one thing and do it well.

## Why use it?

- **Easier Testing**: Test one responsibility in isolation
- **Better Maintenance**: Changes to one concern don't affect others
- **Code Reuse**: Single-purpose classes are easier to reuse
- **Clearer Intent**: What a class does is obvious from its name

## When to use?

- Always! This is a fundamental principle
- When you hear "and" or "also" when describing a class purpose
- When a class would need to change for multiple reasons
- When writing tests feels complicated

## Signs You're Violating SRP

- Class has multiple unrelated methods
- Hard to name the class (needs "Manager", "Handler", "Processor")
- Class has dependencies on unrelated systems
- Tests for class cover many different scenarios
- Multiple developers want to edit the class for different reasons

## Common Mistakes

❌ **God class with many responsibilities**
```typescript
class User {
  saveToDatabase() { }      // Database
  sendEmail() { }           // Email
  validatePassword() { }    // Validation
  hashPassword() { }        // Security
  logActivity() { }         // Logging
  // Why does User need to know about all these?
}
```

✅ **Separated concerns**
```typescript
class User { }                          // Just user data
class UserRepository { }                // Database access
class EmailService { }                  // Sending emails
class PasswordValidator { }             // Validation
class UserCreationOrchestrator { }      // Coordinates everything
```

## How to Apply SRP

1. **Identify responsibilities**: What reasons could this class change?
2. **Extract each responsibility**: Move to its own class
3. **Use dependency injection**: Pass dependencies in constructor
4. **Use interfaces**: Define contracts between classes

## Key Points

- One class = one reason to change
- Use clear, specific class names
- Inject dependencies rather than creating them
- Orchestrator classes can coordinate multiple concerns

## Related Concepts

- **Dependency Injection**: Providing dependencies to classes
- **Separation of Concerns**: Each module handles one aspect
- **Cohesion**: Classes should be tightly bound internally but loosely coupled externally
