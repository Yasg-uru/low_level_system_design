# Inheritance

## What is it?

Inheritance allows a class (child/subclass) to inherit properties and methods from another class (parent/superclass). It creates a hierarchy of related classes and promotes code reuse.

## Why use it?

- **Code Reuse**: Write once, inherit many times
- **Establish Relationships**: Parent-child relationships reflect real-world hierarchies
- **Polymorphism**: Use child classes through parent type references
- **Maintain Consistency**: Shared behavior in one place

## When to use?

- Creating class hierarchies (Employee → Manager, Developer, Intern)
- Sharing common functionality across related classes
- Establishing "is-a" relationships (Developer is-an Employee)
- Building extensible frameworks

## Types of Inheritance

- **Single**: Class extends one parent
- **Multi-level**: Class extends Class which extends Class
- **Hierarchical**: Multiple classes extend same parent

## Common Mistakes

❌ **Deep inheritance hierarchies**
```typescript
class A {}
class B extends A {}
class C extends B {}
class D extends C {} // Too deep!
```

✅ **Shallow, focused hierarchies**
```typescript
abstract class Employee {}
class Manager extends Employee {}
class Developer extends Employee {}
```

## Key Points

- Use abstract classes to define common interface
- Keep hierarchies shallow (1-2 levels)
- Prefer composition over deep inheritance
- Override methods carefully (follow Liskov Substitution Principle)

## Related Concepts

- **Polymorphism**: Using inheritance to write flexible code
- **Abstract Classes**: Defining must-implement methods
- **Method Overriding**: Changing parent behavior in children
