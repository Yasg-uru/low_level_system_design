# Command Design Pattern

## What is it?

The **Command Pattern** is a behavioral design pattern that turns a request or action into a stand-alone object containing all information about the request. This transformation lets you parameterize methods with different requests, delay or queue a request's execution, and support undoable operations.

It promotes loose coupling between the sender of a request (the **Invoker**) and the receiver that knows how to perform it (the **Receiver**).

## Why use it?

- **Undo/Redo Operations**: Commands can easily store the state required to revert their changes.
- **Queuing & Batching**: Requests can be queued, scheduled, or executed in batches.
- **Decoupling**: The object initiating the request is completely decoupled from the object executing the request.
- **Composite Commands (Macros)**: You can combine multiple simple commands into a complex command (e.g., transactional workflows).
- **Audit Logs / Execution History**: Since each action is represented as an object, you can easily log every executed command for auditing purposes.

## When to use?

- When you want to parameterize objects by an action to perform.
- When you need to support operations that can be undone and redone.
- When you want to queue requests, schedule their execution, or run them across networks.
- When you need to build a system where operations must be logged, tracked, or rolled back transactionally.

## Common Mistakes

❌ **Bad approach (Tight Coupling, Hardcoded Actions, and Manual Rollback)**
Here, the order flow is directly written inside a service. If we need to undo a discount, support transaction rollbacks, or execute order campaigns in a batch queue, the system gets messy with manual undo code scattered everywhere.
```typescript
class OrderService {
  placeOrder(userId: string, items: any[]) {
    console.log(`Placing order for ${userId}`);
    // What if this fails? How do we rollback?
  }
  
  applyDiscount(orderId: string, amount: number) {
    console.log(`Applying discount of ${amount} to order ${orderId}`);
    // If we want to undo this discount, we have to write custom revert code in the caller!
  }
}
```

✅ **Good approach (Command Pattern)**
Define an interface for the commands, encapsulate each request inside a class, and use an invoker to manage history.
```typescript
interface ICommand {
  execute(): Promise<boolean>;
  undo(): Promise<void>;
}

class ApplyDiscountCommand implements ICommand {
  private previousTotal?: number;

  constructor(
    private orderService: OrderService,
    private orderId: string,
    private discountPercent: number
  ) {}

  async execute(): Promise<boolean> {
    const order = await this.orderService.findById(this.orderId);
    if (!order) return false;
    this.previousTotal = order.total;
    order.total = order.total * (1 - this.discountPercent / 100);
    return true;
  }

  async undo(): Promise<void> {
    if (this.previousTotal !== undefined) {
      await this.orderService.updateTotal(this.orderId, this.previousTotal);
    }
  }
}

class CommandInvoker {
  private history: ICommand[] = [];

  async execute(command: ICommand) {
    const success = await command.execute();
    if (success) {
      this.history.push(command);
    }
  }

  async undo() {
    const lastCommand = this.history.pop();
    if (lastCommand) {
      await lastCommand.undo();
    }
  }
}
```

## Key Points

- **Receiver**: The object that performs the actual business logic (e.g., `OrderService`, `EmailService`).
- **Command Interface**: Declares `execute()`, `undo()`, etc.
- **Concrete Command**: Implements the command interface and binds a receiver action to parameters.
- **Invoker**: Responsible for triggering commands, storing command execution history, and coordinating rollback actions.
- **Client**: Creates concrete commands and configures their receivers.

## Related Concepts

- **Memento Pattern**: Can be used alongside the Command Pattern to save and restore snapshot states of receivers during undo operations.
- **Strategy Pattern**: While both Strategy and Command parameterize objects with algorithms/requests, Strategy is about *how* to perform an action (interchangeably), whereas Command is about *what* action to perform (encapsulating a request).
