# Abstract Factory Pattern (Java)

## What is it?

The **Abstract Factory Pattern** is a creational design pattern that provides an interface for creating families of related or dependent objects without specifying their concrete classes. It allows a client to use an abstract interface to create a set of related products, ensuring that products from the same family are always used together.

## Why use it?

- **Dependency Inversion**: Clients depend on interfaces (`UserRepository`, `OrderRepository`, etc.) rather than concrete database implementation classes, promoting loose coupling.
- **Product Family Consistency**: Ensures that related products created by a factory are compatible with each other (e.g., matching database repositories or UI components).
- **Single Responsibility Principle**: You can extract the product creation code into factories, making the codebase cleaner and easier to maintain.
- **Open/Closed Principle**: You can introduce new variants of product families (e.g., a MySQL factory) without breaking existing client code.

## When to use?

- When a system needs to be independent of how its products are created, composed, and represented.
- When a system needs to be configured with one of multiple families of products (e.g., PostgreSQL vs. MongoDB repositories, or Stripe vs. PayPal gateways).
- When a family of related product objects is designed to be used together, and you need to enforce this constraint.

## Common Mistakes

❌ **Bad approach (Tight coupling & manual checks)**
```java
// Client service directly instantiates concrete repositories and handles switching logic
public class UserService {
    private final String dbType;

    public UserService(String dbType) {
        this.dbType = dbType;
    }

    public void getUserProfile(int userId) {
        UserRepository userRepo;
        OrderRepository orderRepo;

        // Mixing configuration/creation logic with business logic
        if ("postgres".equalsIgnoreCase(dbType)) {
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
```java
// Client relies on Abstract Factory interface to create related product families
public interface RepositoryFactory {
    UserRepository createUserRepository();
    OrderRepository createOrderRepository();
}

public class UserService {
    private final RepositoryFactory factory;

    public UserService(RepositoryFactory factory) {
        this.factory = factory;
    }

    public void getUserProfile(int userId) {
        // Client is completely unaware of the underlying database type!
        UserRepository userRepo = this.factory.createUserRepository();
        OrderRepository orderRepo = this.factory.createOrderRepository();

        userRepo.getUser(userId);
        orderRepo.getOrder(1001);
    }
}
```

## Java Features Used

- **Interfaces**: Defines the abstract products (`UserRepository`, `OrderRepository`) and the abstract factory (`RepositoryFactory`).
- **Dependency Injection**: Injected via constructor in `UserService`.
- **Enums**: Used in the application configuration/bootstrapping (`DatabaseType`).

## Files in This Package

- `UserRepository.java`: User repository abstract product interface.
- `OrderRepository.java`: Order repository abstract product interface.
- `PostgresUserRepository.java` / `PostgresOrderRepository.java`: PostgreSQL concrete products.
- `MongoUserRepository.java` / `MongoOrderRepository.java`: MongoDB concrete products.
- `RepositoryFactory.java`: Abstract factory interface.
- `PostgresFactory.java` / `MongoFactory.java`: Concrete factories.
- `UserService.java`: Client service class.
- `Main.java`: Bootstrapper and runner class.
- `examples/PaymentExample.java`: Real-world example simulating Stripe and PayPal integration.
