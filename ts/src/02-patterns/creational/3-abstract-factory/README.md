# Abstract Factory Pattern

## What is it?

The **Abstract Factory Pattern** is a creational design pattern that provides an interface for creating families of related or dependent objects without specifying their concrete classes. It allows a client to use an abstract interface to create a set of related products, ensuring that products from the same family are always used together.

## Why use it?

- **Dependency Inversion**: Clients depend on abstract interfaces rather than concrete classes, promoting loose coupling.
- **Product Family Consistency**: Ensures that related products created by a factory are compatible with each other (e.g., matching UI components or database repositories).
- **Single Responsibility Principle**: You can extract the product creation code into one place, making the codebase easier to support.
- **Open/Closed Principle**: You can introduce new variants of products/families without breaking existing client code.

## When to use?

- When a system needs to be independent of how its products are created, composed, and represented.
- When a system needs to be configured with one of multiple families of products (e.g., PostgreSQL vs. MongoDB repositories, or Stripe vs. PayPal payment gateways).
- When a family of related product objects is designed to be used together, and you need to enforce this constraint.

## Common Mistakes

❌ **Bad approach (Tight coupling & manual checks)**
```typescript
// Client service directly instantiates concrete repositories and handles switching logic
class UserService {
  private dbType: 'postgres' | 'mongo';

  constructor(dbType: 'postgres' | 'mongo') {
    this.dbType = dbType;
  }

  public getUserProfile(userId: number): void {
    let userRepo;
    let orderRepo;

    // Mixing configuration/creation logic with business logic
    if (this.dbType === 'postgres') {
      userRepo = new PostgresUserRepository();
      orderRepo = new PostgresOrderRepository();
    } else {
      userRepo = new MongoUserRepository();
      orderRepo = new MongoOrderRepository();
    }

    userRepo.getUser(userId);
    orderRepo.getOrder(1001);
  }
}
```

✅ **Good approach (Abstract Factory with Dependency Injection)**
```typescript
// Client relies on Abstract Factory interface to create related product families
export interface RepositoryFactory {
  createUserRepository(): UserRepository;
  createOrderRepository(): OrderRepository;
}

export class UserService {
  constructor(private readonly factory: RepositoryFactory) {}

  public getUserProfile(userId: number): void {
    // Client is completely unaware of the underlying database type!
    const userRepo = this.factory.createUserRepository();
    const orderRepo = this.factory.createOrderRepository();

    userRepo.getUser(userId);
    orderRepo.getOrder(1001);
  }
}
```

## Differences from Other Patterns

| Pattern | Purpose | Flexibility |
|---|---|---|
| **Factory Method** | Creates objects of a single type via inheritance | Subclasses override creation method |
| **Simple Factory** | Creates objects in a single helper class | Hardcoded creation logic, simple |
| **Abstract Factory** | Creates families of related products via composition | Swappable factory objects |

## Key Points

- Abstract Factory defines a set of methods for creating products, where each method corresponds to a product type.
- Concrete Factories implement these methods to return specific concrete products.
- Enforces consistency among products within the same family.
- Avoid using it when your product families are not cohesive or are frequently changing, as adding a new product requires modifying the abstract factory interface and all concrete factories.

## Related Concepts

- **[Factory Method](../2-factory-method/)**: Abstract Factory is often implemented using Factory Methods under the hood.
- **[Singleton](../1-singleton/)**: Concrete Factories are often implemented as Singletons because only one instance of a factory is typically needed.
- **Dependency Injection**: Used to inject the concrete factory instance into the client service.
