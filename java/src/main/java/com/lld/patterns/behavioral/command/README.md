# Command Design Pattern - Java Implementation

## What is it?

The **Command Pattern** is a behavioral design pattern that encapsulates a request as an object, thereby letting you parameterize clients with different requests, queue or log requests, and support undoable operations.

By turning the request into a standalone object, it decouples the sender (the **Invoker**) from the receiver that performs the work (the **Receiver**).

## Why use it?

- **Reversible Operations (Undo/Redo)**: Each command object can maintain state history required to reverse the action.
- **Transactional State Management**: Group multiple commands (macro commands) to run as a single atomic unit.
- **Queues and Thread Pools**: Commands can be submitted to a processing queue or executed asynchronously.
- **Decoupled Architecture**: Invokers have no dependency on receiver details; they only call the standard command interface.

## When to use?

- When you need to parameterize objects by an action to perform.
- When you need to support multi-level undo and redo.
- When actions need to be logged or persisted for crash recovery or auditing.
- When you need to execute requests at different times, queue them, or execute them in batch pipelines.

## Implementation Details

In this Java package:
1. **`OrderStatus`**: Enum for the lifecycle state of orders.
2. **`CartItem` & `Order`**: Domain entities representing business states.
3. **`ICommand`**: The command interface specifying `execute()`, `undo()`, `getDescription()`, and `canUndo()`.
4. **Concrete Commands**:
   - `PlaceOrderCommand`: Places a new order (undoable).
   - `ApplyDiscountCommand`: Modifies the total order price and snapshots state to restore on rollback.
   - `BulkUpdateOrderStatusCommand`: Updates status for multiple orders.
   - `SendMarketingEmailCommand`: A non-undoable command.
5. **`CommandInvoker`**: Tracks history, redo stack, queues, and flushes operations.

## Related Concepts

- **Memento Pattern**: Useful for saving state snapshots of complex objects for command rollbacks.
- **Strategy Pattern**: Strategy defines *how* to perform an action interchangeably, whereas Command defines *what* request is sent and executed.
