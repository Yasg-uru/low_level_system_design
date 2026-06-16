# Adapter Design Pattern

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
```typescript
// Client directly depends on concrete external SDKs and manages conversions inline
class OrderService {
  public async checkout(gateway: 'stripe' | 'razorpay', amount: number) {
    if (gateway === 'stripe') {
      const client = new StripeClient("sk_live...");
      // Stripe uses cents
      await client.paymentIntents.create({ amount: amount * 100, currency: 'inr' });
    } else {
      const client = new RazorpayClient({ key_id: "...", key_secret: "..." });
      // Razorpay uses paise
      await client.orders.create({ amount: amount * 100, currency: 'INR' });
    }
  }
}
```

✅ **Good approach (Adapter Design Pattern)**
```typescript
// Target domain interface
export interface IPaymentGateway {
  createOrder(amount: number, currency: string): Promise<PaymentOrder>;
}

// Adapter wrapper for Stripe
export class StripeAdapter implements IPaymentGateway {
  private client = new StripeClient("sk_live...");
  public async createOrder(amount: number, currency: string) {
    const response = await this.client.paymentIntents.create({ amount: amount * 100, currency });
    return { id: response.id, amount: response.amount / 100 };
  }
}

// Client utilizes the target interface
class OrderService {
  constructor(private readonly gateway: IPaymentGateway) {}
  public async checkout(amount: number) {
    await this.gateway.createOrder(amount, 'INR');
  }
}
```

## Key Points

- **Target**: The interface that client code expects to use.
- **Adaptee**: The class/library that needs adapting (usually third-party/legacy).
- **Adapter**: The wrapper class implementing the Target interface, routing requests to the Adaptee.
- **Object Adapter**: Uses composition (holds an instance of the Adaptee). This is the standard modern implementation.

## Related Concepts

- **[Bridge](../bridge/)**: Often designed up-front to let you develop parts of an application independently. Adapter is typically used with existing apps to make incompatible classes work together.
- **[Decorator](../decorator/)**: Enhances an object without changing its interface. Adapter changes the interface.
- **[Facade](../facade/)**: Defines a new interface for an entire subsystem. Adapter wraps a single object/interface.
