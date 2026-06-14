# Polymorphism

## What is it?

Polymorphism means "many forms". It allows objects of different types to be used through a common interface. A single function/method can work with different object types, each providing its own implementation.

## Why use it?

- **Flexibility**: Write code that works with multiple types
- **Extensibility**: Add new types without changing existing code
- **Cleaner Code**: Avoid massive if-else chains
- **Loose Coupling**: Depend on interfaces, not concrete types

## When to use?

- Processing collections of different types uniformly
- Building plugins/extensions
- Implementing callback systems
- Creating flexible algorithms

## Two Forms

### 1. Method Overriding (Runtime Polymorphism)
- Child classes override parent methods
- Actual implementation chosen at runtime based on object type
- Most common in practice

### 2. Method Overloading (Compile-time Polymorphism)
- Same method name with different parameters
- Implementation chosen at compile time
- Limited in TypeScript

## Common Mistakes

❌ **Scattered type checks**
```typescript
function processShape(shape: Shape) {
  if (shape instanceof Circle) {
    // ...
  } else if (shape instanceof Rectangle) {
    // ...
  }
  // Bad! Breaks when adding new shape types
}
```

✅ **Polymorphic implementation**
```typescript
function processShape(shape: Shape) {
  console.log(shape.getArea()); // Works for all shapes!
}
```

## Key Points

- Depend on interfaces/abstractions, not concrete types
- Use inheritance or interfaces to establish contracts
- Let each type implement its own behavior
- Avoid type checking (`instanceof`, `typeof`)

## Related Concepts

- **Abstraction**: Defining common interfaces
- **Inheritance**: Establishing type hierarchies
- **Composition**: Combining objects for behavior
