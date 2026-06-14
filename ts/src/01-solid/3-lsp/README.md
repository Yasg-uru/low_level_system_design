# Liskov Substitution Principle (LSP)

## What is it?

Derived classes must be substitutable for their base classes. If S is a subtype of T, then objects of type S may be substituted for objects of type T without breaking the program.

## Why use it?

- **Correctness**: Ensures code works with any subclass
- **Predictability**: Subclasses behave as expected
- **Polymorphism**: Enables safe polymorphic code
- **Contract Integrity**: Maintains class contracts

## When to use?

- Building inheritance hierarchies
- When using polymorphism
- Creating base classes for related types
- Always, when you have inheritance

## The Classic Violation

```typescript
class Rectangle {
  setWidth(w) { this.width = w; }
  setHeight(h) { this.height = h; }
  getArea() { return this.width * this.height; }
}

class Square extends Rectangle {
  setWidth(w) { this.width = w; this.height = w; } // Breaks contract!
}

function test(rect: Rectangle) {
  rect.setWidth(5);
  rect.setHeight(10);
  assert(rect.getArea() === 50); // Fails if rect is Square!
}
```

## How to Fix

1. **Same interface methods** must behave the same
2. **Don't strengthen preconditions** (don't require more to call a method)
3. **Don't weaken postconditions** (must deliver promised results)
4. **Preserve invariants** (class constraints must hold)
5. **Use composition over inheritance** when subclass behavior differs fundamentally

## Common Mistakes

❌ **Child breaks parent contract**
```typescript
class Bird {
  fly(): void { }
}

class Penguin extends Bird {
  fly(): void {
    throw new Error('Penguins cannot fly!');
  }
}
```

✅ **Proper abstraction**
```typescript
interface IFlyable { fly(): void; }
interface ISwimmable { swim(): void; }

class Eagle implements IFlyable { }
class Penguin implements ISwimmable { }
```

## Key Points

- Subclass behavior must be consistent with parent
- Don't use inheritance for unrelated concepts
- Use interfaces for clear contracts
- Test that subclasses work where parent is expected

## Related Concepts

- **Abstraction**: Defining clear contracts
- **Inheritance**: Creating type hierarchies
- **Composition**: Combining behaviors without inheritance
