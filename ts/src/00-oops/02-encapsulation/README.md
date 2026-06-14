# Encapsulation

## What is it?

Encapsulation is bundling data (properties) and methods that operate on that data within a single unit (class), while hiding internal implementation details. Access to data is controlled through public methods (getters/setters).

## Why use it?

- **Data Integrity**: Validate data before modification
- **Reduce Coupling**: Internal changes don't break external code
- **Single Responsibility**: Each class manages its own state
- **Security**: Prevent invalid states
- **Flexibility**: Change internal representation without affecting users

## When to use?

- Creating business objects (User, BankAccount, Order)
- Implementing validation rules
- Managing state that must remain consistent
- Building libraries with clear public APIs

## Three Levels of Access

```typescript
public      // Anyone can access (default in JS)
protected   // Only class and subclasses can access
private     // Only the class itself can access
```

## Common Mistakes

❌ **Too much exposure**
```typescript
class User {
  age: number; // Public by default
}
user.age = -5; // Invalid!
```

✅ **Controlled access**
```typescript
class User {
  private age: number;
  
  setAge(age: number): void {
    if (age < 0 || age > 150) throw new Error('Invalid age');
    this.age = age;
  }
}
```

## Key Points

- Use `private` by default, expose only what's necessary
- Validate in setters before modifying state
- Return copies of mutable objects, not references
- Make readonly properties immutable when possible

## Related Concepts

- **Abstraction**: Hiding how something works
- **Validation**: Ensuring data consistency
- **Immutability**: Making objects unchangeable after creation
