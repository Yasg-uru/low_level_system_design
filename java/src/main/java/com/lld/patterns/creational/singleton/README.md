# Singleton Pattern (Java)

## What is it?

Singleton ensures that a class has only one instance and provides a global access point to it.

## Two Approaches

### 1. Eager Initialization
```java
public class Singleton {
    private static final Singleton INSTANCE = new Singleton();
    
    private Singleton() { }
    
    public static Singleton getInstance() {
        return INSTANCE;
    }
}
```

**Pros**: Simple, thread-safe
**Cons**: Instance created even if never used

### 2. Lazy Initialization
```java
public class Singleton {
    private static Singleton instance;
    
    private Singleton() { }
    
    public static synchronized Singleton getInstance() {
        if (instance == null) {
            instance = new Singleton();
        }
        return instance;
    }
}
```

**Pros**: Only created when needed
**Cons**: Need synchronization for thread safety

## When to use?

- **Logger**: One logger for entire application
- **Database Connection Pool**: Reuse connections
- **Configuration**: Single config object
- **Cache**: Global cache instance

## Problems with Singleton

❌ **Hard to test**: Can't mock or reset
❌ **Global state**: Makes code hard to reason about
❌ **Tight coupling**: Classes depend on Singleton directly

## Better Alternative: Dependency Injection

```java
// Instead of:
class Service {
    private Logger logger = Logger.getInstance();
}

// Prefer:
class Service {
    private final Logger logger;
    
    public Service(Logger logger) {
        this.logger = logger;
    }
}
```

## Java Features Used

- **Private constructor**: Prevents instantiation
- **Static field**: Holds single instance
- **Synchronized method**: Thread safety
- **Static import**: Global access

## Key Points

- Private constructor prevents direct instantiation
- Static getInstance() provides access
- Ensure thread safety (synchronized or eager init)
- Consider DI instead of Singleton when possible

## Files in This Package

- `Singleton.java`: Eager initialization
- `LazyInitializedSingleton.java`: Lazy initialization (thread-safe)
- `Logger.java`: Real-world example
