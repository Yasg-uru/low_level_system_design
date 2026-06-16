# Adapter Design Pattern (Java)

## What is it?

The **Adapter Pattern** is a structural design pattern that allows objects with incompatible interfaces to collaborate. It acts as a wrapper/translator between your core business domain logic and third-party libraries, legacy code, or external SDKs that don't match the interface your system expects.

## Why use it?

- **Decoupled Integrations**: Isolates third-party client details (e.g., Stripe SDK, Razorpay SDK) from your core application logic. If you decide to change vendors, you only change the adapter, leaving business logic untouched.
- **Single Responsibility Principle**: You can separate the interface or data conversion code from the primary business logic of the program.
- **Open/Closed Principle**: You can introduce new adapters into the program without breaking existing client code, as long as they work with the target interface.

## When to use?

- When you want to use an existing class, but its interface does not match the rest of your code.
- When you want to build a reusable class that cooperates with unrelated or unforeseen classes (e.g. diverse payment gateways, multiple notification services, various logging engines).

## Common Mistakes

❌ **Bad approach (Tight coupling & manual checks)**
```java
// Client directly depends on concrete external SDKs and manages conversions inline
class OrderService {
    public void checkout(String gateway, double amount) {
        if ("stripe".equalsIgnoreCase(gateway)) {
            StripeClient client = new StripeClient("sk_live...");
            // Stripe uses cents
            client.createPaymentIntent((int) (amount * 100), "usd");
        } else {
            RazorpayClient client = new RazorpayClient("key", "secret");
            // Razorpay uses paise
            client.createOrder((int) (amount * 100), "INR");
        }
    }
}
```

✅ **Good approach (Adapter Design Pattern)**
```java
// Target domain interface
public interface IPaymentGateway {
    PaymentOrder createOrder(double amount, String currency, OrderMetadata metadata) throws Exception;
}

// Adapter wrapper for Stripe
public class StripeAdapter implements IPaymentGateway {
    private final StripeClient client = new StripeClient("sk_live...");

    @Override
    public PaymentOrder createOrder(double amount, String currency, OrderMetadata metadata) {
        int amountInCents = (int) Math.round(amount * 100);
        StripePaymentIntent intent = client.paymentIntents.create(amountInCents, currency);
        return new PaymentOrder(intent.id, intent.amount / 100.0, intent.currency);
    }
}

// Client utilizes the target interface
class OrderService {
    private final IPaymentGateway gateway;

    public OrderService(IPaymentGateway gateway) {
        this.gateway = gateway;
    }

    public void checkout(double amount) throws Exception {
        this.gateway.createOrder(amount, "INR", new OrderMetadata(...));
    }
}
```

## Java Features Used

- **Composition**: Standard Object Adapter pattern using instance variables holding Adaptee references.
- **Exception Handling**: Standard Java checked exceptions propagation.
- **Math/Rounding APIs**: Utilized `Math.round` to convert currency values accurately to smallest sub-units without losing precision.

## Files in This Package

- `IPaymentGateway.java`: Common payment gateway interface.
- `OrderMetadata.java`: Domain parameters metadata object.
- `PaymentOrder.java`: Domain DTO for order created.
- `PaymentCapture.java`: Domain DTO for capture operations.
- `RefundResult.java`: Domain DTO for refunds.
- `PaymentStatus.java`: Generic payment state enums.
- `CartItem.java` / `Order.java`: Order models.
- `IOrderRepository.java` / `PostgresOrderRepository.java`: Database layer representations.
- `INotificationService.java` / `EmailNotificationService.java`: Dispatching system configurations.
- `RazorpayClient.java` / `StripeClient.java`: Simulated third-party SDK clients (Adaptee mocks).
- `RazorpayAdapter.java` / `StripeAdapter.java`: Gateway wrappers (Adapters).
- `OrderService.java`: Domain service (Client).
- `Main.java`: Execution runner.
