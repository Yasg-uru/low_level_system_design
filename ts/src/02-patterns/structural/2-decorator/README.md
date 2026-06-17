# Decorator Design Pattern

## What is it?

The **Decorator Pattern** is a structural design pattern that allows behavior to be added to an individual object, dynamically, without affecting the behavior of other objects from the same class. It wraps the original object in a decorator class that implements the same interface, extending its functionality at runtime.

## Why use it?

- **Flexible Alternative to Inheritance**: Instead of creating dozens of subclasses for every combination of options (e.g., `MilkSugarCoffee`, `WhippedCreamSugarCoffee`), you can compose behaviors dynamically.
- **Single Responsibility Principle**: You can divide a monolithic class that has many possible behaviors into several smaller, focused decorator classes.
- **Runtime Modification**: Behaviors can be added or removed at runtime simply by wrapping or unwrapping the component.

## When to use?

- When you need to assign extra behaviors to objects at runtime without breaking the code that uses these objects.
- When it's awkward or impossible to extend an object’s behavior using inheritance (e.g. `final` classes in Java, or when inheritance leads to class explosion).

## Common Mistakes

❌ **Bad approach (Class Explosion via Inheritance)**
```typescript
// Inheriting for every single combination creates an unmaintainable class hierarchy
class SimpleCoffee {}
class CoffeeWithMilk extends SimpleCoffee {}
class CoffeeWithSugar extends SimpleCoffee {}
class CoffeeWithMilkAndSugar extends SimpleCoffee {}
class CoffeeWithWhippedCreamMilkAndSugar extends SimpleCoffee {}
```

✅ **Good approach (Decorator Pattern)**
```typescript
export interface Coffee {
  getCost(): number;
  getDescription(): string;
}

export class SimpleCoffee implements Coffee {
  getCost() { return 5; }
  getDescription() { return "Simple coffee"; }
}

export abstract class BaseDecorator implements Coffee {
  constructor(protected coffee: Coffee) {}
  getCost() { return this.coffee.getCost(); }
  getDescription() { return this.coffee.getDescription(); }
}

export class MilkDecorator extends BaseDecorator {
  getCost() { return this.coffee.getCost() + 2; }
  getDescription() { return this.coffee.getDescription() + ", milk"; }
}
```

## Key Points

- **Shared Interface**: Both the core component and the decorators must implement the same interface.
- **Composition**: The decorator contains a reference to a component instance (`coffee: Coffee`).
- **Delegation**: Decorators delegate work to the wrapped object, performing additional tasks before or after the delegated call.

## Related Concepts

- **[Adapter](../1-adapter/)**: Changes the interface of an existing object. Decorator enhances the behavior without changing the interface.
- **[Composite](../composite/)**: Decorator can be viewed as a Composite with only one component. But Decorator adds responsibilities, while Composite summates child results.
