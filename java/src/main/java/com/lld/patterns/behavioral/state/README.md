# State Design Pattern - Java Implementation

## What is it?

The **State Pattern** is a behavioral design pattern that allows an object to alter its behavior when its internal state changes. The object will appear to change its class.

By encapsulating state-specific behavior inside individual state classes, we keep classes lightweight, modular, and easy to maintain.

## Why use it?

- **Eliminates Complex Switch Blocks**: Removes complex `if-else` or `switch` structures inside context methods.
- **Single Responsibility Principle**: Each state class is responsible only for behaviors applicable within that state.
- **Open/Closed Principle**: We can easily add new states without changing the client or other state classes.
- **Clear State Transitions**: Transitions are defined explicitly within state actions.

## Implementation Details

In this Java package:
1. **`IOrderState`**: The interface declaring actions supported across all states (`confirm`, `ship`, `deliver`, `cancel`, `requestReturn`, `getStatus`, `getAllowedActions`).
2. **`Order`**: The context class representing the order, maintaining the active state reference.
3. **Concrete State Classes**:
   - `PendingState`: Allows confirming or cancelling the order.
   - `ConfirmedState`: Allows shipping or cancelling the order.
   - `ShippedState`: Allows delivering the order.
   - `DeliveredState`: Allows returning within a 30-day window.
   - `CancelledState`: Terminal state.
   - `ReturnedState`: Terminal state.
4. **`Main`**: Runs scenarios showing correct transitions and exception handling for invalid state actions.

## Related Concepts

- **Strategy Pattern**: Strategy manages interchangeable algorithms, while State manages state-dependent behaviors and transitions.
- **Flyweight Pattern**: If state objects carry no instance fields, they can be shared to optimize memory.
