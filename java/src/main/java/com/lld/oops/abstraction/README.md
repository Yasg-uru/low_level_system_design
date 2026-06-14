# Abstraction (Java)

## What is it?

Abstraction is hiding implementation details and showing only essential features. In Java, we achieve this through:
- **Abstract Classes**: When subclasses share common code
- **Interfaces**: When defining pure contracts

## Key Java Features

### Abstract Classes
```java
abstract class Vehicle {
    abstract void start();
    
    public void refuel(double amount) {
        // Common implementation
    }
}
```

### Interfaces
```java
interface IPaymentProcessor {
    boolean processPayment(double amount);
}
```

## Why use it?

- Hides complexity
- Allows changing implementation without affecting clients
- Enables polymorphism
- Makes code easier to maintain and test

## Files in This Package

- **Vehicle.java**: Abstract class example
- **Car.java, Motorcycle.java**: Concrete implementations
- **IPaymentProcessor.java**: Interface example
- **StripePayment.java, PayPalPayment.java**: Concrete implementations

## Key Differences from TypeScript

| Feature | Java | TypeScript |
|---------|------|-----------|
| Abstract Classes | `abstract class` | Not native, use classes |
| Interfaces | `interface` keyword | `interface` keyword |
| Implementation | `implements` keyword | `implements` keyword |
| Abstract Methods | `abstract` keyword | N/A |
| Access Control | `public`, `protected`, `private` | Similar but defaults differ |

## Common Mistakes

❌ **Exposing internal state**
```java
public class BankAccount {
    public double balance;  // Should be private!
}
```

✅ **Controlled access**
```java
public class BankAccount {
    private double balance;
    
    public boolean withdraw(double amount) {
        if (amount > balance) return false;
        balance -= amount;
        return true;
    }
}
```

## Key Points

- Use `abstract` keyword for abstract classes
- Use `interface` for pure contracts
- Use `implements` to implement interfaces
- Make fields `private`, expose through methods
- Use meaningful names for abstract methods

## Related Concepts

- **Encapsulation**: Bundling data and methods
- **Polymorphism**: Using abstraction to write flexible code
- **Inheritance**: Extending abstract classes
