# Decorator Design Pattern (Java)

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
```java
// Inheriting for every single combination creates an unmaintainable class hierarchy
public class SimpleCoffee {}
public class CoffeeWithMilk extends SimpleCoffee {}
public class CoffeeWithSugar extends SimpleCoffee {}
public class CoffeeWithMilkAndSugar extends SimpleCoffee {}
```

✅ **Good approach (Decorator Pattern)**
```java
public interface Coffee {
    double getCost();
    String getDescription();
}

public class SimpleCoffee implements Coffee {
    public double getCost() { return 5.0; }
    public String getDescription() { return "Simple coffee"; }
}

public abstract class BaseDecorator implements Coffee {
    protected final Coffee coffee;
    public BaseDecorator(Coffee coffee) { this.coffee = coffee; }
    public double getCost() { return this.coffee.getCost(); }
    public String getDescription() { return this.coffee.getDescription(); }
}

public class MilkDecorator extends BaseDecorator {
    public MilkDecorator(Coffee coffee) { super(coffee); }
    public double getCost() { return super.getCost() + 2.0; }
    public String getDescription() { return super.getDescription() + ", milk"; }
}
```

## Java Features Used

- **Composition**: Storing `Coffee` references inside the abstract `BaseDecorator` class.
- **Polymorphism**: Interchanging `SimpleCoffee` and its decorators cleanly anywhere the `Coffee` interface is expected.
- **Abstract Classes**: Declaring `BaseDecorator` as abstract to prevent raw instantiations.

## Files in This Package

- `Coffee.java`: Core Component interface.
- `SimpleCoffee.java`: Concrete Component class.
- `BaseDecorator.java`: Base wrapper class.
- `MilkDecorator.java`: Concrete Decorator adding milk cost and text.
- `SugarDecorator.java`: Concrete Decorator adding sugar cost and text.
- `WhippedCreamDecorator.java`: Concrete Decorator adding whipped cream cost and text.
- `Main.java`: Execution runner showing dynamic wrapping of drinks.
