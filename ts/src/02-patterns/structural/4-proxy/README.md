# Proxy Design Pattern

## What is it?

The **Proxy Pattern** is a structural design pattern that provides a surrogate, placeholder, or wrapper for another object. A proxy controls access to the original object, allowing you to perform tasks (such as validation, caching, logging, or lazy initialization) either before or after the request reaches the target object.

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

1. **[Virtual Proxy](./virtual-proxy/)**: Delays the creation and initialization of a resource-intensive object (e.g., database connections, large media files) until it is actually called by the client.
2. **[Protection Proxy](./protection-proxy/)**: Controls access to the real object based on authorization/authentication rules (e.g., restricting administrative database mutations).
3. **[Caching Proxy](./caching-proxy/)**: Stores the results of expensive operations (e.g., API requests) to return cached values instantly for identical input parameters.
4. **[Logging Proxy](./logging-proxy/)**: Captures and records trace logs of all method calls, arguments, execution times, and exceptions on the real subject.
5. **[Remote Proxy](./remote-proxy/)**: Represents an object residing in a different address space or remote server, encapsulating serialization, deserialization, and network protocols.

## Common Mistakes

❌ **Bad approach (Bloating the Business Logic Class)**
```typescript
// Mixing authorization, logging, caching, and database queries in the core class.
// Violates the Single Responsibility Principle and the Open-Closed Principle.
class BankAccount {
  private balance: number = 0;

  public withdraw(amount: number, userRole: string): void {
    // 1. Access checking (Protection Proxy concern)
    if (userRole !== 'ADMIN' && amount > 1000) {
      throw new Error("Access Denied");
    }

    // 2. Logging call parameters (Logging Proxy concern)
    console.log(`[Audit Log] Withdrawing ${amount}...`);

    // 3. Business rule
    if (amount > this.balance) throw new Error("Insufficient funds");
    this.balance -= amount;
  }
}
```

✅ **Good approach (Separating Concerns via Proxy)**
```typescript
interface IBankAccount {
  withdraw(amount: number): void;
}

class RealBankAccount implements IBankAccount {
  private balance: number = 1000;
  
  public withdraw(amount: number): void {
    if (amount > this.balance) throw new Error("Insufficient funds");
    this.balance -= amount;
  }
}

// Proxy encapsulates the protection concern and delegates if authorized
class ProtectionBankAccountProxy implements IBankAccount {
  constructor(private realAccount: RealBankAccount, private userRole: string) {}

  public withdraw(amount: number): void {
    if (this.userRole !== 'ADMIN' && amount > 1000) {
      throw new Error("Access Denied");
    }
    this.realAccount.withdraw(amount);
  }
}
```

## Differences from Other Patterns

| Pattern | Purpose | Main Focus |
|---|---|---|
| **Proxy** | Controls access to an object while exposing the identical interface. | Access control, lifecycle management, performance (caching/lazy-loading). |
| **Decorator** | Dynamically adds responsibilities or behaviors to an object. | Enhancing or layering functionality; client-controlled compilation. |
| **Adapter** | Translates one interface to another. | Compatibility and code integration. |

## Key Points

- **Identical Interface**: The Proxy implements the exact same interface as the Real Subject, making them fully interchangeable for the client.
- **Composition Root**: The proxy wraps the real subject (either by composing an existing instance or instantiating it internally).
- **Thin Wrappers**: The Proxy handles cross-cutting concerns (caching, logging, security check) and leaves business logic to the real subject.
