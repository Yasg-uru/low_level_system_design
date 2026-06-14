# Inheritance (Java)

## What is it?

Inheritance allows a class to inherit properties and methods from another class. It creates class hierarchies and promotes code reuse.

## Java Keywords

```java
// Class inheritance
public class Manager extends Employee { }

// Abstract base class
public abstract class Employee { }

// Override parent methods
@Override
public double calculateBonus() { }
```

## Why use it?

- **Code Reuse**: Write common functionality once
- **Relationships**: Model real-world hierarchies
- **Polymorphism**: Use subclasses through parent type
- **Consistency**: Shared behavior in one place

## Inheritance Types

### Single Inheritance
```java
class Developer extends Employee { }
```

### Multi-level Inheritance
```java
class SoftwareDeveloper extends Developer extends Employee { }
```

### Hierarchical Inheritance
```java
class Manager extends Employee { }
class Developer extends Employee { }
class Intern extends Employee { }
```

## Common Mistakes

❌ **Deep hierarchies**
```java
class A { }
class B extends A { }
class C extends B { }
class D extends C { }  // Too deep!
```

✅ **Shallow, focused hierarchies**
```java
abstract class Employee { }
class Manager extends Employee { }
class Developer extends Employee { }
```

## Key Java Features

### @Override Annotation
```java
@Override
public double calculateBonus() {
    return salary * 0.15;
}
```

### super Keyword
```java
public String getDetails() {
    return super.getDetails() + " - Additional info";
}
```

### Abstract Methods
```java
abstract class Employee {
    abstract double calculateBonus();
}
```

## Key Points

- Keep hierarchies shallow (1-2 levels)
- Use abstract classes for common interface
- Override meaningfully in subclasses
- Use `@Override` annotation to ensure correct overriding
- Prefer composition over deep inheritance

## Related Concepts

- **Polymorphism**: Using inheritance for flexible code
- **Abstract Classes**: Defining contracts
- **Method Overriding**: Changing parent behavior in children
