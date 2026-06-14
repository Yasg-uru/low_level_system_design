# Abstraction

## What is it?

Abstraction is the process of hiding implementation details and showing only the essential features of an object. It reduces complexity by letting you interact with objects at a higher level without knowing how they work internally.

## Why use it?

- **Reduces complexity**: Hide internal details, focus on what matters
- **Easier maintenance**: Change implementation without affecting code that uses it
- **Flexible design**: Swap implementations without changing client code
- **Better reusability**: Abstract concepts can be reused across projects

## When to use?

- Creating a service/utility that multiple classes depend on
- Designing a library API that users shouldn't care about internals
- Building extensible systems where implementations may vary
- Reducing coupling between different parts of your code

## Two Approaches

### 1. Abstract Classes
- Used when subclasses share common code
- Can have both abstract and concrete methods
- Can have state (properties)
- Establishes a strong relationship

### 2. Interfaces
- Pure contracts (no implementation)
- Define what methods must exist
- Multiple interfaces can be implemented
- Looser coupling, more flexible

## Common Mistakes

❌ **Don't expose internal state**
```typescript
class BankAccount {
  balance: number; // Exposed!
  // Client can do: account.balance = -1000;
}
```

✅ **Hide state behind methods**
```typescript
class BankAccount {
  private balance: number;
  
  withdraw(amount: number): boolean {
    if (amount > this.balance) return false;
    this.balance -= amount;
    return true;
  }
}
```

## Key Points

- **Hide complexity**: Keep internal details private
- **Show only essential**: Public methods should be minimal and clear
- **Maintain invariants**: Ensure invalid states are impossible
- **Use meaningful names**: Method names should describe purpose, not implementation

## Related Concepts

- **Encapsulation**: Bundling data and methods together
- **Inheritance**: Extending abstract classes/interfaces
- **Polymorphism**: Using abstraction to write flexible code
