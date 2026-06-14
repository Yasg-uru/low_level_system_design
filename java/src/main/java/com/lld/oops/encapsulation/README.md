# Encapsulation (Java)

## What is it?

Bundling data and methods together while hiding internal state. Access is controlled through public methods.

## Java Features

### Access Modifiers
```java
public class BankAccount {
    private double balance;           // Only accessible within class
    protected String accountNumber;   // Accessible to subclasses
    public double getBalance() { }    // Public interface
}
```

### Getters and Setters
```java
private int age;

public void setAge(int age) {
    if (age < 0 || age > 150) {
        throw new IllegalArgumentException("Invalid age");
    }
    this.age = age;
}

public int getAge() {
    return age;
}
```

## Why use it?

- **Data Integrity**: Validation before modification
- **Control**: Decide what's public and what's private
- **Flexibility**: Change implementation without affecting clients
- **Security**: Prevent invalid states

## Key Java Patterns

### 1. Private Fields
```java
private double balance;
```

### 2. Public Accessors
```java
public double getBalance() {
    return balance;
}
```

### 3. Validation in Setters
```java
public boolean withdraw(double amount) {
    if (amount > balance) return false;
    balance -= amount;
    return true;
}
```

### 4. Return Defensive Copies
```java
public List<Transaction> getTransactionHistory() {
    return new ArrayList<>(transactionHistory);  // Copy, not reference
}
```

## Common Mistakes

❌ **Public fields**
```java
public class User {
    public int age;  // Anyone can set to -5!
}
```

✅ **Encapsulated fields**
```java
public class User {
    private int age;
    
    public void setAge(int age) {
        if (age < 0 || age > 150) {
            throw new IllegalArgumentException("Invalid age");
        }
        this.age = age;
    }
}
```

## Key Points

- Make fields `private` by default
- Expose through public getter/setter methods
- Validate in setters
- Return defensive copies of mutable objects
- Use `final` for immutable fields

## Related Concepts

- **Abstraction**: Hiding how something works
- **Inheritance**: Extending encapsulated classes
- **Polymorphism**: Using encapsulation with inheritance
