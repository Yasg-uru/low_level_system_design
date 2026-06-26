/**
 * ADVANCED STATE DESIGN PATTERN EXAMPLE
 * * Intent: Encapsulate state-dependent behaviors into distinct, clean classes.
 * * Key Benefit: Eliminates complicated conditional checks for multi-stage lifecycle actions.
 */

// --- 1. THE STATE INTERFACE ---
interface IOrderState {
  confirm(order: Order): void;
  ship(order: Order): void;
  deliver(order: Order): void;
  cancel(order: Order): void;
  requestReturn(order: Order): void;
  getStatus(): string;
  getAllowedActions(): string[];
}

// --- 2. THE CONTEXT CLASS ---
class Order {
  private state: IOrderState;

  constructor() {
    // Initializing with the default setup state
    this.state = new PendingState();
  }

  setState(state: IOrderState): void {
    this.state = state;
  }

  confirm(): void {
    this.state.confirm(this);
  }

  ship(): void {
    this.state.ship(this);
  }

  deliver(): void {
    this.state.deliver(this);
  }

  cancel(): void {
    this.state.cancel(this);
  }

  requestReturn(): void {
    this.state.requestReturn(this);
  }

  getStatus(): string {
    return this.state.getStatus();
  }

  getAllowedActions(): string[] {
    return this.state.getAllowedActions();
  }
}

// --- 3. CONCRETE STATE IMPLEMENTATIONS ---

// State 1: Pending State
class PendingState implements IOrderState {
  confirm(order: Order): void {
    console.log("-> Order confirmed successfully.");
    order.setState(new ConfirmedState());
  }

  ship(_order: Order): void {
    throw new Error("Cannot ship a pending order. Confirm it first.");
  }

  deliver(_order: Order): void {
    throw new Error("Cannot deliver a pending order. Confirm it first.");
  }

  cancel(order: Order): void {
    console.log("-> Order cancelled from pending state.");
    order.setState(new CancelledState());
  }

  requestReturn(_order: Order): void {
    throw new Error("Cannot request return for a pending order.");
  }

  getStatus(): string {
    return "Pending";
  }

  getAllowedActions(): string[] {
    return ["confirm", "cancel"];
  }
}

// State 2: Confirmed State
class ConfirmedState implements IOrderState {
  confirm(_order: Order): void {
    throw new Error("Order is already confirmed.");
  }

  ship(order: Order): void {
    console.log("-> Order passed to transit delivery channels.");
    order.setState(new ShippedState());
  }

  deliver(_order: Order): void {
    throw new Error("Cannot deliver a confirmed order. Ship it first.");
  }

  cancel(order: Order): void {
    console.log("-> Order cancelled from confirmed state.");
    order.setState(new CancelledState());
  }

  requestReturn(_order: Order): void {
    throw new Error("Cannot request return for a confirmed order.");
  }

  getStatus(): string {
    return "Confirmed";
  }

  getAllowedActions(): string[] {
    return ["ship", "cancel"];
  }
}

// State 3: Shipped State
class ShippedState implements IOrderState {
  confirm(_order: Order): void {
    throw new Error("Order is already confirmed and shipped.");
  }

  ship(_order: Order): void {
    throw new Error("Order is already shipped.");
  }

  deliver(order: Order): void {
    console.log("-> Order successfully handed over to destination user.");
    order.setState(new DeliveredState());
  }

  cancel(_order: Order): void {
    throw new Error("Cannot cancel a shipped order.");
  }

  requestReturn(_order: Order): void {
    throw new Error("Cannot request return for a shipped order. Deliver it first.");
  }

  getStatus(): string {
    return "Shipped";
  }

  getAllowedActions(): string[] {
    return ["deliver"];
  }
}

// State 4: Delivered State (Features Time-Bound Return Window Logic)
class DeliveredState implements IOrderState {
  private deliveredDate: Date;

  constructor() {
    this.deliveredDate = new Date();
  }

  getStatus(): string {
    return "Delivered";
  }

  getAllowedActions(): string[] {
    const currentDate = new Date();
    const diffInDays = (currentDate.getTime() - this.deliveredDate.getTime()) / (1000 * 3600 * 24);
    
    return diffInDays <= 30 ? ["requestReturn"] : [];
  }

  requestReturn(order: Order): void {
    const currentDate = new Date();
    const diffInDays = (currentDate.getTime() - this.deliveredDate.getTime()) / (1000 * 3600 * 24);

    if (diffInDays <= 30) {
      console.log("-> Return request accepted within valid eligibility windows.");
      order.setState(new ReturnedState());
    } else {
      throw new Error("Return period has expired. Cannot request return.");
    }
  }

  confirm(_order: Order): void { throw new Error("Order is already delivered."); }
  ship(_order: Order): void { throw new Error("Order is already delivered."); }
  deliver(_order: Order): void { throw new Error("Order is already delivered."); }
  cancel(_order: Order): void { throw new Error("Cannot cancel a delivered order."); }
}

// State 5: Cancelled State (Terminal State)
class CancelledState implements IOrderState {
  confirm(_order: Order): void { throw new Error("Cannot confirm a cancelled order."); }
  ship(_order: Order): void { throw new Error("Cannot ship a cancelled order."); }
  deliver(_order: Order): void { throw new Error("Cannot deliver a cancelled order."); }
  cancel(_order: Order): void { throw new Error("Order is already cancelled."); }
  requestReturn(_order: Order): void { throw new Error("Cannot request return for a cancelled order."); }

  getStatus(): string { return "Cancelled"; }
  getAllowedActions(): string[] { return []; }
}

// State 6: Returned State (Terminal State)
class ReturnedState implements IOrderState {
  confirm(_order: Order): void { throw new Error("Cannot confirm a returned order."); }
  ship(_order: Order): void { throw new Error("Cannot ship a returned order."); }
  deliver(_order: Order): void { throw new Error("Cannot deliver a returned order."); }
  cancel(_order: Order): void { throw new Error("Cannot cancel a returned order."); }
  requestReturn(_order: Order): void { throw new Error("Order is already returned."); }

  getStatus(): string { return "Returned"; }
  getAllowedActions(): string[] { return []; }
}

// --- 4. DEMONSTRATION RUN ---

function runOrderLifecycleDemo(): void {
  console.log("--- 🛒 Scenario A: Standard Operational Path ---");
  const myOrder = new Order();
  console.log(`Current: ${myOrder.getStatus()} | Options: ${JSON.stringify(myOrder.getAllowedActions())}`);

  myOrder.confirm();
  console.log(`Current: ${myOrder.getStatus()} | Options: ${JSON.stringify(myOrder.getAllowedActions())}`);

  myOrder.ship();
  console.log(`Current: ${myOrder.getStatus()} | Options: ${JSON.stringify(myOrder.getAllowedActions())}`);

  myOrder.deliver();
  console.log(`Current: ${myOrder.getStatus()} | Options: ${JSON.stringify(myOrder.getAllowedActions())}`);

  myOrder.requestReturn();
  console.log(`Current: ${myOrder.getStatus()} | Options: ${JSON.stringify(myOrder.getAllowedActions())}`);

  console.log("\n--- 🛒 Scenario B: Exception / Validation Guard Interception ---");
  const failedOrder = new Order();
  try {
    failedOrder.ship(); // Invalid action call path
  } catch (err) {
    console.error(`Caught expected rule rejection: ${(err as Error).message}`);
  }
}

runOrderLifecycleDemo();