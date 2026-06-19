# Proxy Design Pattern (Java)

## What is it?

The **Proxy Pattern** is a structural design pattern that provides a surrogate, placeholder, or wrapper for another object. A proxy controls access to the original object, allowing you to perform tasks (such as validation, caching, logging, or lazy initialization) either before or after the request reaches the target object.

In Java, proxies can be implemented statically (as shown in these examples, where the proxy implements the same interface and delegates calls to the concrete object) or dynamically using Java's reflection mechanism (`java.lang.reflect.Proxy`).

## Why use it?

- **Access Control**: Restricts access to the real object based on permissions, roles, or security credentials.
- **Performance Optimization**: Defers the instantiation of resource-heavy objects (lazy loading) or caches the results of expensive operations.
- **Non-Intrusive Extensions**: Extends or inspects the behavior of a service class (e.g., logging operations or measuring latency) without modifying its source code.
- **Resource Management**: Manages the lifecycle of a resource (e.g., closing database connections, freeing up memory, or routing remote network calls).

## When to use?

- When you need to control access to a sensitive resource (Protection Proxy).
- When you want to defer expensive object creation until needed (Virtual Proxy).
- When you want to cache identical query or network requests (Caching Proxy).
- When you need an audit trail or metrics for object method calls (Logging Proxy).
- When the target object is on a remote server/service (Remote Proxy).

## Proxy Variations in This Repository

This package contains 5 distinct variations of the Proxy pattern:

1. **[Virtual Proxy](./virtual/)**: Delays the creation and initialization of a resource-intensive object (e.g., database connections, large media files) until it is actually called by the client.
2. **[Protection Proxy](./protection/)**: Controls access to the real object based on authorization/authentication rules (e.g., restricting administrative database mutations).
3. **[Caching Proxy](./caching/)**: Stores the results of expensive operations (e.g., API requests) to return cached values instantly for identical input parameters.
4. **[Logging Proxy](./logging/)**: Captures and records trace logs of all method calls, arguments, execution times, and exceptions on the real subject.
5. **[Remote Proxy](./remote/)**: Represents an object residing in a different address space or remote server, encapsulating serialization, deserialization, and network protocols.

## Common Mistakes

❌ **Bad approach (Bloating the Business Logic Class)**
```java
// Mixing authorization, logging, caching, and database queries in the core class.
// Violates the Single Responsibility Principle and the Open-Closed Principle.
public class BankAccount {
    private double balance = 0;

    public void withdraw(double amount, String userRole) throws Exception {
        // 1. Access checking (Protection Proxy concern)
        if (!"ADMIN".equals(userRole) && amount > 1000) {
            throw new Exception("Access Denied");
        }

        // 2. Logging call parameters (Logging Proxy concern)
        System.out.println("[Audit Log] Withdrawing " + amount + "...");

        // 3. Business rule
        if (amount > this.balance) {
            throw new Exception("Insufficient funds");
        }
        this.balance -= amount;
    }
}
```

✅ **Good approach (Separating Concerns via Proxy)**
```java
public interface IBankAccount {
    void withdraw(double amount) throws Exception;
}

public class RealBankAccount implements IBankAccount {
    private double balance = 1000;
    
    @Override
    public void withdraw(double amount) throws Exception {
        if (amount > this.balance) {
            throw new Exception("Insufficient funds");
        }
        this.balance -= amount;
    }
}

// Proxy encapsulates the protection concern and delegates if authorized
public class ProtectionBankAccountProxy implements IBankAccount {
    private final RealBankAccount realAccount;
    private final String userRole;

    public ProtectionBankAccountProxy(RealBankAccount realAccount, String userRole) {
        this.realAccount = realAccount;
        this.userRole = userRole;
    }

    @Override
    public void withdraw(double amount) throws Exception {
        if (!"ADMIN".equals(this.userRole) && amount > 1000) {
            throw new Exception("Access Denied");
        }
        this.realAccount.withdraw(amount);
    }
}
```

## Java Features Used

- **Enums**: Used `Permission` enum to represent user access levels.
- **Java Collection Framework**: Used `Map` and `List` implementations (`HashMap`, `ArrayList`) for caching, logging, and permission management.
- **Thread Concurrency**: Used `Thread.sleep(...)` to simulate network and disk latency for virtual, caching, and remote proxies.
- **Java time APIs**: Used `Instant.now()` to timestamp logs and transactions.
- **Callable Interface**: Used `java.util.concurrent.Callable` inside the remote proxy helper to wrap remote requests dynamically.

## Files in This Package

- `caching/`:
  - `DataService.java`: Component interface.
  - `RealDataService.java`: Concrete service simulating expensive queries.
  - `CachingDataServiceProxy.java`: Proxy caching return results.
  - `Main.java`: Local execution runner for caching proxy.
- `logging/`:
  - `LoggingBankAccountInterface.java`: Component interface.
  - `RealBankAccount.java`: Concrete bank account performing business logic.
  - `LoggingBankAccountProxy.java`: Proxy writing transaction logs.
  - `Main.java`: Local execution runner for logging proxy.
- `protection/`:
  - `Permission.java`: Enum representing permission roles.
  - `DocumentUser.java`: User entity holding permissions.
  - `Document.java`: Component interface.
  - `RealDocument.java`: Concrete document editor.
  - `ProtectionDocumentProxy.java`: Proxy handling role validation checks.
  - `Main.java`: Local execution runner for protection proxy.
- `remote/`:
  - `RemoteUserServiceInterface.java`: Component interface.
  - `RemoteUserService.java`: Concrete service simulating remote API.
  - `UserServiceProxy.java`: Proxy routing calls over the network.
  - `Main.java`: Local execution runner for remote proxy.
- `virtual/`:
  - `Image.java`: Component interface.
  - `RealImage.java`: Concrete resource-heavy loader.
  - `ProxyImage.java`: Proxy lazy-loading resources on demand.
  - `Main.java`: Local execution runner for virtual proxy.

