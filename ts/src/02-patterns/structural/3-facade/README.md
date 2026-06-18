# Facade Design Pattern

## What is it?

The **Facade Pattern** is a structural design pattern that provides a simplified, unified interface to a larger, more complex body of code, such as a class library, a framework, or a set of complex subsystems/micro-services. It acts as a single point of entry, wrapping high-level interactions and shielding clients from low-level subsystem complexities.

## Why use it?

- **Reduced Complexity**: Simplifies the entry point to a complex subsystem by exposing a few simple, well-defined methods instead of numerous subsystem-specific APIs.
- **Loose Coupling**: Minimizes direct dependencies between clients and complex subsystems, making the client code easier to read, maintain, and test.
- **Prevention of Business Logic Leakage**: Prevents low-level orchestration rules (e.g., db transactions, external api error handling, rollbacks) from leaking into presentation layers (like HTTP controllers or command-line interfaces).
- **Subsystem Layering**: Helps structure a subsystem into logical layers. A facade can serve as the interface for a specific layer, directing traffic and coordinating cross-layer dependencies.

## When to use?

- When you want to provide a simple, unified interface to a complex subsystem (e.g., orchestrating an e-commerce checkout involving inventory pools, payment gateways, databases, email systems, and auditing).
- When there are many dependencies between clients and the implementation classes of an abstraction.
- When you want to layer your subsystems, defining entry points for each level of abstraction.

## Common Mistakes

❌ **Bad approach (Client Orchestrates Subsystems Directly)**
```typescript
// Controllers, background jobs, and CLI clients must manually coordinate all subsystems.
// Any change in the business rules requires editing duplicate logic across all clients.
class CheckoutController {
  public async handleCheckout(req: Request, res: Response) {
    // 1. Check & reserve stock
    const stock = await inventoryRepo.getStock(req.body.productId);
    if (stock < req.body.quantity) return res.status(400).json({ error: "Out of stock" });
    await inventoryRepo.decrementStock(req.body.productId, req.body.quantity);

    // 2. Charge via payment gateway
    try {
      const charge = await paymentGateway.charge(req.body.price * req.body.quantity);
      if (!charge.success) throw new Error("Declined");
      
      // 3. Create order in Database
      const order = await orderRepo.save({ ...req.body, paymentId: charge.id });
      
      // 4. Send email notifications
      await notificationSender.send(req.user.email, "Confirmed", order.id);

      // 5. Write to audit logs
      await auditRepo.save({ action: "ORDER_PLACED", orderId: order.id });
      
      res.json({ order });
    } catch (err) {
      // Rollback inventory manually
      await inventoryRepo.incrementStock(req.body.productId, req.body.quantity);
      await auditRepo.save({ action: "PAYMENT_FAILED", error: err.message });
      res.status(400).json({ error: "Checkout failed" });
    }
  }
}
```

✅ **Good approach (Facade Orchestration)**
```typescript
// The facade handles all subsystem coordination, transactions, and recovery rules.
class CheckoutFacade {
  constructor(
    private inventory: InventoryService,
    private payment: PaymentService,
    private order: OrderService,
    private notifications: NotificationService,
    private audit: AuditService
  ) {}

  public async checkout(request: CheckoutRequest): Promise<CheckoutResult> {
    // Reserves inventory, handles payment, creates order records,
    // dispatches async email notifications, and audits the transaction.
    // Automatically rolls back reserved stock if payment fails.
  }
}

// Client controllers are kept clean, dry, and simple.
class CheckoutController {
  constructor(private facade: CheckoutFacade) {}

  public async handleCheckout(req: Request, res: Response) {
    const result = await this.facade.checkout({ userId: req.user.id, items: req.body.items });
    if (!result.success) return res.status(400).json({ error: result.error });
    res.json({ order: result.order });
  }
}
```

## Differences from Other Patterns

| Pattern | Purpose | Main Focus |
|---|---|---|
| **Facade** | Provides a simplified interface to a complex subsystem. | Simplicity and orchestration. |
| **Adapter** | Converts an existing interface to match another interface that a client expects. | Interface translation/compatibility. |
| **Mediator** | Centralizes direct communication between co-dependent colleague objects. | Decentralizing peer-to-peer communication. |

## Key Points

- **Facade doesn't prevent access**: Subsystem classes remain accessible; clients can still interact with them directly if they need advanced or low-level configurations.
- **Rollback / Transaction handling**: A facade is an excellent place to manage saga-like transactions and compensating actions across separate services/repositories.
- **Single Responsibility**: The Facade is responsible only for orchestrating subsystem interactions, not for carrying out individual business rules (which should remain within the subsystems).

## Related Concepts

- **[Adapter](../1-adapter/)**: Translates interfaces. Facade defines a new, simpler interface, whereas Adapter reuse an existing interface.
- **Saga Pattern**: An architectural pattern that extends the orchestrating rollback concept of a Facade to distributed systems.
