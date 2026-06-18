# Facade Design Pattern (Java)

## What is it?

The **Facade Pattern** is a structural design pattern that provides a simplified, unified interface to a complex set of subsystem classes, frameworks, or micro-services. By exposing a high-level API, it hides the complexities of individual subsystems and coordinates interactions between them, acting as the single entry point for clients.

In Java backend architectures, a Facade is typically positioned between the presentation/controller layers and the business domain services. It isolates clients from transaction coordination, external payment gateways, inventory allocation rules, and mail servers, keeping client code simple and declarative.

## Why use it?

- **Reduced Complexity & Client Decoupling**: Clients do not need to know which repositories or external services exist; they interact with a single orchestrator.
- **Prevents Transaction/Rollback Leaks**: Complex compensating transactions (e.g., releasing reserved inventory if a payment fails) are encapsulated inside the Facade.
- **Enforces Single Responsibility**: Controllers focus solely on HTTP protocol concerns (status codes, JSON serialization), while services focus on individual domain logic. The Facade takes the responsibility of coordination.
- **Cleaner Client APIs**: Prevents telescoping orchestration boilerplate across different entry points (Web, Mobile, Admin, and Queue Schedulers).

## When to use?

- When you need a simple interface to access a complex subsystem of co-dependent classes.
- When there are many dependencies between clients and the implementation classes of an abstraction.
- When you want to define entry points for different layers of your application.

## Common Mistakes

❌ **Bad approach (Client Orchestrates Subsystems Directly)**
```java
// Controller is bloated with DB queries, payment calls, rollback handlers, and notifications.
// The same sequence must be duplicated inside Mobile Controllers and Batch Jobs.
public class CheckoutController {
    public void handleCheckout(Request req, Response res) {
        // 1. Reserve Stock
        int stock = inventoryRepo.getStock(productId);
        if (stock < quantity) {
            res.status(400).json(Map.of("error", "Out of stock"));
            return;
        }
        inventoryRepo.decrementStock(productId, quantity);

        try {
            // 2. Process Payment
            PaymentResult pr = paymentService.charge(amount, userId);
            if (!pr.isSuccess()) throw new RuntimeException("Declined");

            // 3. Save Order
            Order order = orderService.create(new CreateOrderDTO(...));
            
            // 4. Send email & write audits
            notificationService.sendOrderConfirmation(email, order);
            auditService.log("ORDER_PLACED", metadata);
            res.json(order);
        } catch (Exception e) {
            // Rollback inventory manually - error prone!
            inventoryRepo.incrementStock(productId, quantity);
            res.status(400).json(Map.of("error", "Payment failed"));
        }
    }
}
```

✅ **Good approach (Facade Orchestration)**
```java
// Unified transaction facade
public class CheckoutFacade {
    public CheckoutResult checkout(CheckoutRequest request) {
        // Reserves stock, attempts payment, handles compensation/rollback,
        // creates the database entry, triggers notifications, and writes audit logs.
    }
}

// Controller remains thin and descriptive
public class CheckoutController {
    private final CheckoutFacade facade;

    public void handleCheckout(Request req, Response res) {
        CheckoutResult result = facade.checkout(new CheckoutRequest(userId, items));
        if (!result.isSuccess()) {
            res.status(400).json(Map.of("error", result.getError()));
            return;
        }
        res.json(Map.of("order", result.getOrder()));
    }
}
```

## Java Features Used

- **Streams & Lambdas**: Used `items.stream().mapToDouble(...)` to calculate the order total cleanly.
- **Concurrency (Multi-threading)**: Executed fire-and-forget user notifications in a separate thread pool / `new Thread()` to avoid holding up the HTTP checkout pipeline.
- **Generics & Collections**: Used standard Java `Map` and `List` structures to emulate dynamically typed request/response payload parsing.
- **Nested Interfaces & Static Inner Classes**: Decoupled queue retry jobs using a custom static inner interface `QueueDriver`.

## Files in This Package

- `CartItem.java`: Data model representing a line item in a shopping cart.
- `Order.java`: Immutable data model representing a finalized order.
- `CreateOrderDTO.java`: Data transfer object representing parameters needed to create an order.
- `PaymentResult.java`: Model representing the result of a payment charge request.
- `CheckoutRequest.java` / `CheckoutResult.java`: Input/output contracts for the checkout operation.
- `User.java` / `AuditLog.java`: Simplistic models representing users and audit trace logs.
- `Request.java` / `Response.java`: Mock servlet-like HTTP structures to simulate real-world controllers.
- `IOrderRepository.java` / `IInventoryRepository.java` / `IUserRepository.java` / `IAuditRepository.java`: Database access abstraction layer interfaces.
- `IPaymentGateway.java` / `INotificationSender.java`: External third-party gateway interfaces.
- `OrderService.java` / `PaymentService.java` / `InventoryService.java` / `NotificationService.java` / `AuditService.java`: Subsystem business logic services.
- `CheckoutFacade.java`: The primary facade pattern implementation orchestrating the checkout and cancellation processes.
- `CheckoutController.java` / `MobileCheckoutController.java` / `AdminOrderController.java`: Controller client classes using the Facade.
- `CheckoutRetryJob.java`: Background worker queue processor using the Facade.
- `Main.java`: Driver program initializing mocks, seeding data, and executing test scenarios.
