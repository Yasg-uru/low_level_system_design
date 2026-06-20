# Logging Proxy

## Overview
A Logging Proxy records every method call made to an object, including parameters, return values, and execution time. This is useful for monitoring, debugging, and auditing purposes.

## When to Use
- When you need to monitor method calls for debugging
- When you want to track usage patterns and performance
- When you need an audit trail of operations
- When you want to log errors and exceptions
- When you need to measure execution time of operations

## Benefits
- **Debugging**: Helps track down issues by logging method calls
- **Monitoring**: Provides visibility into system behavior
- **Performance**: Can measure execution time of operations
- **Auditing**: Creates a record of all operations performed
- **Non-intrusive**: Adds logging without modifying the original object

## Example Scenario
A banking system where every transaction must be logged for audit purposes - the logging proxy records all deposit, withdrawal, and transfer operations.

## Structure
```
Subject (Interface)
    ↓
RealSubject (Contains business logic)
    ↓
Proxy (Logging Proxy - Records all method calls)
```

## Common Mistakes

❌ **Bad approach (Adding logging trace directly in business methods)**
```java
// Bank account class is cluttered with logger statements, violating SRP.
public class RealBankAccount implements LoggingBankAccountInterface {
    private double balance = 0;

    public void deposit(double amount) {
        System.out.println("[LOG] Calling deposit(" + amount + ")");
        balance += amount;
        System.out.println("[LOG] Result: Deposit successful");
    }
}
```

✅ **Good approach (Using Logging Proxy)**
```java
// Bank account class only handles business rules. The proxy handles trace logging.
public class RealBankAccount implements LoggingBankAccountInterface {
    private double balance = 0;
    public void deposit(double amount) {
        balance += amount;
    }
}

public class LoggingBankAccountProxy implements LoggingBankAccountInterface {
    private RealBankAccount realAccount;

    public void deposit(double amount) {
        System.out.println("[LOG] Calling deposit(" + amount + ")");
        realAccount.deposit(amount);
        System.out.println("[LOG] Result: Deposit successful");
    }
}
```

## Key Points
- The proxy logs method calls before and after execution
- Can log parameters, return values, and execution time
- Can log errors and exceptions
- The logging can be written to console, files, or external logging services
- The real subject remains unchanged and unaware of logging
- Can be combined with other proxy types (caching, protection, etc.)
