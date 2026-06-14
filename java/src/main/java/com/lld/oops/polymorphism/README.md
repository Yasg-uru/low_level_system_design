# Polymorphism (Java)

## What is it?

Polymorphism means "many forms". It allows objects of different types to be used through a common interface.

## Java Features

### Method Overriding
```java
interface Shape {
    double calculateArea();
}

class Circle implements Shape {
    @Override
    public double calculateArea() {
        return Math.PI * radius * radius;
    }
}
```

### Runtime Polymorphism
```java
Shape shape = new Circle(5);  // Circle can be used as Shape
double area = shape.calculateArea();  // Calls Circle's implementation
```

## Why use it?

- **Flexibility**: Write code that works with multiple types
- **Extensibility**: Add new types without changing existing code
- **Cleaner Code**: Avoid massive if-else chains
- **Loose Coupling**: Depend on interfaces, not concrete types

## Polymorphism Types

### 1. Interface-based (Runtime Polymorphism)
```java
Shape shape1 = new Circle(5);
Shape shape2 = new Rectangle(10, 20);
// Can treat both as Shape
```

### 2. Inheritance-based
```java
List<Employee> employees = new ArrayList<>();
employees.add(new Manager(...));
employees.add(new Developer(...));
// Can treat all as Employee
```

## Common Mistakes

❌ **Type checking and casting**
```java
void process(Shape shape) {
    if (shape instanceof Circle) {
        // Specific logic - breaks polymorphism!
    }
}
```

✅ **Polymorphic processing**
```java
void process(List<Shape> shapes) {
    for (Shape shape : shapes) {
        System.out.println(shape.calculateArea());  // Works for all!
    }
}
```

## Key Java Features

### @Override Annotation
Ensures correct method overriding at compile time.

### Generics
```java
List<Shape> shapes = new ArrayList<>();
```

### Functional Interfaces
```java
@FunctionalInterface
interface Operation {
    double execute(double a, double b);
}
```

## Key Points

- Depend on interfaces/abstractions, not concrete types
- Use inheritance or interfaces to establish contracts
- Let each type implement its own behavior
- Avoid type checking (`instanceof`)
- Use polymorphism for flexible, extensible code

## Related Concepts

- **Abstraction**: Defining common interfaces
- **Inheritance**: Creating type hierarchies
- **Interfaces**: Pure contracts
