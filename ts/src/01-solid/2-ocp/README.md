# Open-Closed Principle (OCP)

## What is it?

Software entities should be **open for extension** but **closed for modification**. You should be able to add new functionality without changing existing code.

## Why use it?

- **Reduce Risk**: Adding features doesn't break existing code
- **Scalability**: Easy to add new implementations
- **Maintainability**: New code doesn't touch old code
- **Flexibility**: Different implementations can coexist

## When to use?

- Building extensible systems (payment processors, storage backends)
- Creating plugin architectures
- Designing frameworks or libraries
- When you'll need variations of functionality

## Techniques to Achieve OCP

### 1. Inheritance/Abstraction
```typescript
abstract class PaymentMethod {
  abstract process(amount: number): boolean;
}

class CreditCard extends PaymentMethod { }
class PayPal extends PaymentMethod { }
// Add new payment method without touching existing ones
```

### 2. Composition/Strategy
```typescript
interface IPaymentStrategy {
  process(amount: number): boolean;
}
// Client uses strategy, not direct implementation
```

### 3. Dependency Injection
```typescript
class Service {
  constructor(private processor: IPaymentProcessor) {}
}
// New implementations can be injected without code change
```

## Common Mistakes

❌ **Modification required for extension**
```typescript
class PaymentProcessor {
  process(amount: number, type: string) {
    if (type === 'credit_card') { }
    else if (type === 'paypal') { }
    // Adding new type? Modify this!
  }
}
```

✅ **Extension without modification**
```typescript
class PaymentProcessor {
  process(amount: number, strategy: IPaymentStrategy) {
    return strategy.process(amount);
  }
}
// New strategy? Just create new class!
```

## Key Points

- Use abstractions (interfaces/abstract classes)
- Depend on contracts, not implementations
- Use Strategy, Decorator, or Template Method patterns
- Think about what might change and abstract it

## Related Concepts

- **Abstraction**: Hiding implementation details
- **Polymorphism**: Using abstractions to handle variations
- **Dependency Injection**: Providing implementations at runtime
