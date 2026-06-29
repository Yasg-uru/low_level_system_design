# Mediator Design Pattern

## What is it?

The **Mediator Pattern** is a behavioral design pattern that reduces chaotic dependencies between objects (known as colleagues). It restricts direct communications between the objects and forces them to collaborate only via a central mediator object.

Instead of objects communicating directly and knowing about each other's details, they only know about the Mediator. This transforms a complex **many-to-many** network of connections into a cleaner **one-to-many** star network.

## Why use it?

- **Loose Coupling**: Colleague classes are completely decoupled from each other. You can modify, add, or replace colleagues without modifying other colleagues.
- **Simplified Communication**: Simplifies communication pathways from many-to-many to one-to-many, making the interactions easier to comprehend and maintain.
- **Centralized Control**: Encapsulates how a set of objects interact under a single class, facilitating adjustments to the overall workflow.

## When to use?

- When it's difficult to change some classes because they are tightly coupled to a host of other classes.
- When you cannot reuse a component in a different context because it's too dependent on other components.
- When you find yourself creating numerous subclasses just to customize the way some classes interact.

## Common Mistakes

❌ **Bad approach (Tight coupling / Direct communication)**
Here, each user needs to keep references to all other users in the chat room to send them messages. The dependency graph grows quadratically with the number of users, and managing the active list of users across all individual instances is highly error-prone.
```typescript
class DirectUser {
  constructor(public name: string, private otherUsers: DirectUser[]) {}

  send(message: string) {
    this.otherUsers.forEach(user => user.receive(message, this.name));
  }

  receive(message: string, from: string) {
    console.log(`${this.name} received from ${from}: ${message}`);
  }
}
```

✅ **Good approach (Mediator Pattern)**
By introducing a centralized `ChatMediator`, users are completely decoupled from each other. They communicate only with the mediator, which coordinates and routes messages appropriately.
```typescript
interface IChatMediator {
  sendMessage(message: string, from: ChatUser): void;
  addUser(user: ChatUser): void;
}

class ChatUser {
  constructor(public name: string, private mediator: IChatMediator) {}

  send(message: string): void {
    this.mediator.sendMessage(message, this);
  }

  receive(message: string, from: string): void {
    console.log(`${this.name} received from ${from}: ${message}`);
  }
}
```

## Key Points

- **Mediator Interface**: Defines the communication contract for colleagues (`IChatMediator`).
- **Concrete Mediator**: Implements the coordination logic and coordinates colleagues (`ChatMediator`).
- **Colleague**: The objects that collaborate via the mediator rather than directly with each other (`ChatUser`).

## Related Concepts

- **[Observer Pattern](../1-observer/)**: Mediator and Observer can be used together. The mediator can play the role of an observer, reacting to state changes in colleague components.
- **[Facade Design Pattern](../../structural/5-facade/)**: Facade provides a simplified interface to a subsystem without defining new behaviors, whereas Mediator centralizes communication between collaborating components.
