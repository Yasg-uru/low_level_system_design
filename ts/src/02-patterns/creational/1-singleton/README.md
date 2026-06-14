# Singleton Pattern

## What is it?

Singleton ensures that a class has only one instance and provides a global point of access to it. The class itself manages its own instantiation.

## Why use it?

- **Single point of access**: Guaranteed one instance across application
- **Lazy initialization**: Instance created only when needed
- **Controlled creation**: Class controls how it's instantiated
- **Memory efficient**: Only one instance in memory

## When to use?

- **Loggers**: One logger instance for entire application
- **Database connections**: Reuse single connection pool
- **Configuration**: One config object for whole app
- **Caching**: Single cache instance
- **Thread pools**: One thread pool manager

## ❌ When NOT to use?

- When you might need multiple instances later
- In testing (hard to mock or reset)
- When it complicates dependency injection
- For stateful objects that need different states

## Implementation Patterns

### Eager Initialization
```typescript
class Singleton {
  private static instance: Singleton = new Singleton();
  
  static getInstance(): Singleton {
    return Singleton.instance;
  }
}
```

### Lazy Initialization (Better)
```typescript
class Singleton {
  private static instance: Singleton | null = null;
  
  static getInstance(): Singleton {
    if (Singleton.instance === null) {
      Singleton.instance = new Singleton();
    }
    return Singleton.instance;
  }
}
```

## Common Mistakes

❌ **Providing public constructor**
```typescript
class Config {
  public constructor() { }  // Anyone can create instances!
}
const c1 = new Config();
const c2 = new Config();    // Different instances!
```

✅ **Private constructor**
```typescript
class Config {
  private constructor() { }
  static getInstance(): Config { /* ... */ }
}
```

## Problems with Singleton

1. **Testing difficulty**: Hard to mock in tests
2. **Global state**: Can make code hard to reason about
3. **Hidden dependencies**: getInstance() call hides real dependency
4. **Concurrency**: Multi-threaded access requires synchronization

## Better Alternative: Dependency Injection

```typescript
// Instead of:
class Service {
  private logger = Logger.getInstance();
}

// Prefer:
class Service {
  constructor(private logger: ILogger) {}
}

// Then inject:
const logger = new Logger();
const service = new Service(logger);
```

## Key Points

- Private constructor prevents direct instantiation
- Static getInstance() provides access
- First call creates instance (lazy initialization)
- Subsequent calls return same instance
- Consider DI as alternative for testability

## Related Concepts

- **Factory Pattern**: Creating instances with control
- **Dependency Injection**: Alternative to singletons
- **Module Pattern**: JavaScript alternative using modules
