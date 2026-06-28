# Chain of Responsibility Design Pattern - Java Implementation

## What is it?

The **Chain of Responsibility Pattern** is a behavioral design pattern that passes requests along a pipeline of handlers. Upon receiving a request, each handler decides either to process it directly or to delegate the processing to the successor handler in the chain.

This Java package implements a multi-tier support ticketing escalation pipeline where support tickets are passed from Tier 1 up to Tier 3 support.

## Why use it?

- **Decouples Senders and Receivers**: The client sending the support ticket does not need to know which support tier will ultimately resolve it; it only communicates with the entry-point handler.
- **Single Responsibility Principle**: Isolates individual support ticket resolution rules and Tier-specific mechanics into separate handler classes.
- **Dynamic Configuration**: Allows constructing, re-ordering, or extending the support tiers at runtime without modifying the base classes or the client.

## Implementation Details

In this Java package:
1. **`IHandler`**: The base interface declaring successor chaining (`setNext`) and evaluation (`handle`).
2. **`BaseHandler`**: An abstract base class that holds the successor reference and implements default forwarding fallback logic.
3. **Concrete Support Tiers**:
   - `Level1Support`: Captures and resolves "password reset" tickets.
   - `Level2Support`: Captures and resolves "bug report" tickets.
   - `Level3Support`: Captures and resolves "billing issue" tickets.
4. **`Main`**: Constructs the chain relationship (`L1 -> L2 -> L3`) and routes various support requests down the pipeline.

## Related Concepts

- **Decorator Pattern**: Decorator wraps an object to dynamically add custom behavior, while Chain of Responsibility passes a request along a sequence of separate handlers.
- **Composite Pattern**: Often combined with Chain of Responsibility, enabling components to propagate requests up parent child-tree hierarchies.
