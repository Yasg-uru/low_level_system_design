# State Design Pattern

## What is it?

The **State Pattern** is a behavioral design pattern that allows an object to alter its behavior when its internal state changes. The object will appear to change its class.

By encapsulating state-specific behaviors into separate classes, it eliminates massive conditional blocks (like nested `switch` or `if-else` statements) that depend on the object's current state.

## Why use it?

- **Single Responsibility Principle**: Group behaviors related to a particular state into a single, clean class.
- **Open/Closed Principle**: Introduce new states or alter existing state transitions without modifying the context class or other state implementations.
- **Eliminate Complex Conditionals**: Avoid giant, error-prone conditional statements that check the state of the object before performing actions.
- **Explicit State Transitions**: Transitions between states become first-class transitions controlled cleanly by the states themselves or the context.

## When to use?

- When you have an object that behaves differently depending on its current state, and the number of states is large and state-specific code changes frequently.
- When a class contains massive conditional statements that govern its behavior based on the values of the class's fields.
- When there is a strict sequential flow of states, and operations are only allowed in specific states (e.g., an Order lifecycle: Pending -> Confirmed -> Shipped -> Delivered).

## Common Mistakes

❌ **Bad approach (Giant Conditional Switch Blocks)**
Every method in the class must check the current status. Adding a new state (like "Returned" or "Partially Shipped") requires modifying every single method, which is highly error-prone and violates OCP.
```typescript
class Order {
  private status: string = "Pending";

  confirm() {
    if (this.status === "Pending") {
      console.log("Confirmed!");
      this.status = "Confirmed";
    } else {
      throw new Error("Cannot confirm order in this state.");
    }
  }

  ship() {
    if (this.status === "Confirmed") {
      console.log("Shipped!");
      this.status = "Shipped";
    } else {
      throw new Error("Cannot ship order in this state.");
    }
  }

  // Same status checks repeated inside cancel(), deliver(), return(), etc...
}
```

✅ **Good approach (State Pattern)**
Create an interface for states, encapsulate behaviors within state classes, and delegate operations from the Context class to the active state.
```typescript
interface IOrderState {
  confirm(order: Order): void;
  ship(order: Order): void;
}

class Order {
  private state: IOrderState;

  constructor() {
    this.state = new PendingState();
  }

  setState(state: IOrderState) {
    this.state = state;
  }

  confirm() {
    this.state.confirm(this);
  }

  ship() {
    this.state.ship(this);
  }
}

class PendingState implements IOrderState {
  confirm(order: Order) {
    console.log("Confirmed!");
    order.setState(new ConfirmedState());
  }

  ship(order: Order) {
    throw new Error("Cannot ship a pending order.");
  }
}
```

## Key Points

- **Context (`Order`)**: Defines the interface of interest to clients and maintains an instance of a Concrete State subclass that defines the current state.
- **State Interface (`IOrderState`)**: Defines an interface for encapsulating the behavior associated with a particular state of the Context.
- **Concrete States**: Each subclass implements a behavior associated with a state of the Context.

## Related Concepts

- **Strategy Pattern**: State and Strategy have similar structures. The main difference is that in the State pattern, the states can transition from one to another and are aware of each other, whereas Strategies are configured externally and usually don't know about each other.
- **Flyweight Pattern**: If state objects contain no instance-specific state data, they can be shared as Singletons to save memory.
