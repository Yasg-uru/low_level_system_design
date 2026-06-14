# Dependency Inversion Principle (DIP)

## What is it?

High-level modules should not depend on low-level modules. Both should depend on abstractions. Depend on interfaces/abstractions, not concrete implementations.

## Why use it?

- **Testability**: Easy to mock dependencies
- **Flexibility**: Swap implementations without changing code
- **Loose Coupling**: Modules don't know about each other
- **Maintainability**: Changes to implementations don't cascade

## When to use?

- When you need multiple implementations
- Building testable code
- Creating flexible architectures
- When implementations might change

## The Inversion

**Before (Normal):**
```
UserService → MySQLDatabase → SQL Driver
(High-level) (Low-level)
```

**After (Inverted):**
```
UserService → IDatabase ← MySQLDatabase
(High-level) (Abstraction) (Low-level)
```

Both depend on the abstraction (IDatabase), not on each other.

## Common Mistakes

❌ **Direct dependencies**
```typescript
class UserService {
  constructor() {
    this.database = new MySQLDatabase(); // Hard-coded!
  }
}
```

✅ **Injected abstractions**
```typescript
class UserService {
  constructor(private database: IDatabase) {} // Flexible!
}
```

## How to Apply DIP

1. **Identify dependencies**: What does your class need?
2. **Create abstractions**: Define interfaces for each dependency
3. **Inject dependencies**: Pass them in constructor
4. **Use abstractions**: Depend on interfaces, not implementations

## Dependency Injection Patterns

### Constructor Injection (Most Common)
```typescript
class Service {
  constructor(private repo: IRepository) {}
}
```

### Property Injection
```typescript
class Service {
  repository: IRepository;
}
service.repository = new Repository();
```

### Method Injection
```typescript
class Service {
  doWork(repo: IRepository) { }
}
```

## Key Points

- Always inject dependencies
- Depend on abstractions (interfaces)
- Make it easy to provide different implementations
- Enables testing with mock objects

## Related Concepts

- **Single Responsibility**: Each class has one job
- **Abstraction**: Hiding implementation details
- **Testing**: Using mocks and test doubles
- **Factory Pattern**: Creating instances without hard-coding types
