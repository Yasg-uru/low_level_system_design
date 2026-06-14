# Factory Method Pattern

## What is it?

Factory Method creates objects without specifying their exact classes. Instead of directly instantiating classes, you define an abstract method that subclasses override to create specific object types.

## Components

- **Product**: Interface that all created objects implement
- **ConcreteProduct**: Actual objects being created
- **Creator**: Abstract class defining the factory method
- **ConcreteCreator**: Implements factory method to create specific products

## Why use it?

- **Decoupling**: Client doesn't know about concrete classes
- **Flexibility**: Easy to add new product types
- **Centralized creation**: All object creation logic in one place
- **Consistency**: Ensure all objects are created properly

## When to use?

- System should work with products of different types
- You'll likely add new product types later
- Object creation logic is complex
- You want subclasses to decide which class to instantiate

## Implementation Patterns

### Basic Pattern
```typescript
abstract class Factory {
  abstract createProduct(): IProduct;
  
  use() {
    const product = this.createProduct();
    product.doSomething();
  }
}

class ConcreteFactory extends Factory {
  createProduct(): IProduct {
    return new ConcreteProduct();
  }
}
```

### With Parameters
```typescript
abstract class NotificationFactory {
  abstract createNotification(): INotification;
  
  sendNotification(message: string) {
    const notif = this.createNotification();
    notif.send(message);
  }
}
```

## Common Mistakes

❌ **Direct instantiation (tight coupling)**
```typescript
class Service {
  getNotification() {
    if (type === 'email') {
      return new EmailNotification();
    } else if (type === 'sms') {
      return new SMSNotification();
    }
  }
}
```

✅ **Factory method**
```typescript
abstract class NotificationFactory {
  abstract createNotification(): INotification;
}

class EmailFactory extends NotificationFactory {
  createNotification(): INotification {
    return new EmailNotification();
  }
}
```

## Differences from Other Patterns

| Pattern | Purpose | Flexibility |
|---------|---------|-------------|
| Factory Method | Create objects via inheritance | Subclasses decide type |
| Simple Factory | Create objects in one place | Fixed set of types |
| Abstract Factory | Create families of objects | Related products |

## Key Points

- Abstract method in parent class
- Subclasses override to create specific types
- Client depends on abstraction, not concrete types
- Useful when you'll add new types later
- More complex than simple factory but more flexible

## Related Concepts

- **Simple Factory**: Non-OOP alternative
- **Abstract Factory**: Creating families of related objects
- **Strategy Pattern**: Similar structure, different purpose
