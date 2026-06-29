# Mediator Design Pattern - Java Implementation

## What is it?

The **Mediator Pattern** is a behavioral design pattern that defines an object that encapsulates how a set of objects interact. Mediator promotes loose coupling by keeping objects from referring to each other explicitly, and it lets you vary their interaction independently.

This Java package implements a central chat room (`ChatMediator`) where individual participants (`ChatUser`) send messages to the room rather than referencing and contacting other users directly.

## Why use it?

- **Decoupled Colleague Interaction**: Users don't need to know about or maintain references to other users in the chat system.
- **Single Point of Control**: Changes to the routing algorithm or message policies (like moderation, auditing, or archiving) can be implemented in a single `ChatMediator` class.
- **Star Topology**: Replaces complex mesh topologies (where every object links to every other object) with a simple star topology centered around the mediator.

## Implementation Details

In this Java package:
1. **`IChatMediator`**: The mediator interface outlining the contract for adding users and routing messages.
2. **`ChatMediator`**: Concrete mediator holding a list of active `ChatUser` colleagues and distributing sent messages to all other registered colleagues.
3. **`ChatUser`**: Colleague representing chat room participants. Users do not reference other users directly; they send all messages to their mediator instance.
4. **`Main`**: Demonstrates registering three users to a chat mediator and exchanging messages.

## Related Concepts

- **Observer Pattern**: Often used to establish dynamic communication where components subscribe to events triggered by the mediator.
- **Facade Pattern**: Unlike Facade which provides a simplified interface to a subsystem downstream, Mediator coordinates multi-directional behavior and communication upstream.
