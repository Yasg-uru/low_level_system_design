# Chain of Responsibility Design Pattern

## What is it?

The **Chain of Responsibility Pattern** is a behavioral design pattern that allows passing requests along a chain of potential handlers. Upon receiving a request, each handler decides either to process the request or to delegate it to the next handler in the chain. 

By chaining the receiving objects together, it decouples the sender of a request from the concrete receiver that ultimately handles it.

## Why use it?

- **Reduces Coupling**: The client (sender) is completely decoupled from the specific receiver classes that process the request.
- **Single Responsibility Principle**: You can isolate the logic for handling a particular scenario into a single handler class.
- **Open/Closed Principle**: You can introduce new handlers or re-organize the execution order of the chain at runtime without modifying client code or existing handlers.
- **Dynamic Configuration**: Chains can be constructed and customized dynamically at runtime based on environment configuration or request context.

## When to use?

- When your application needs to evaluate a request through a series of steps or support tiers, where the exact handler isn't known in advance.
- When multiple objects are capable of handling a request, and the specific handler should be determined automatically.
- When you want to execute several handlers in a strict sequential order (e.g., authentication -> logging -> caching -> rate-limiting).

## Common Mistakes

❌ **Bad approach (Monolithic Conditional Router)**
A monolithic class that contains nested `if-else` conditions for every support tier or request type. Adding a new support tier or changing the priority order requires modifying the monolithic router class, violating the Open/Closed Principle.
```typescript
class TicketRouter {
  routeTicket(request: string) {
    if (request === "password reset") {
      console.log("Level 1 Support: Resetting password...");
    } else if (request === "bug report") {
      console.log("Level 2 Support: Logging bug...");
    } else if (request === "billing issue") {
      console.log("Level 3 Support: Resolving bill...");
    } else {
      console.log("Terminal Fallback: Support ticket rejected.");
    }
  }
}
```

✅ **Good approach (Chain of Responsibility)**
Decoupling the support tiers into independent handlers that inherit from a common base handler. Each handler only decides whether it can process the request or if it should pass it forward to its successor.
```typescript
interface IHandler {
  setNext(handler: IHandler): IHandler;
  handle(request: string): string;
}

abstract class BaseHandler implements IHandler {
  private next: IHandler | null = null;

  setNext(handler: IHandler): IHandler {
    this.next = handler;
    return handler;
  }

  handle(request: string): string {
    if (this.next) return this.next.handle(request);
    return "Unhandled";
  }
}

class Level1Support extends BaseHandler {
  handle(request: string): string {
    if (request === "password reset") return "Processed by L1";
    return super.handle(request);
  }
}
```

## Key Points

- **Handler Interface (`IHandler`)**: Declares the method for handling requests and establishing the successor link (`setNext`).
- **Base Handler (`BaseHandler`)**: Optional helper class containing boilerplate chaining code and passing requests to the successor.
- **Concrete Handlers (`Level1Support`, etc.)**: Implement the actual request processing logic. If they cannot handle it, they delegate to `super.handle(request)`.
- **Client**: Configures the chain (e.g., `l1.setNext(l2).setNext(l3)`) and initiates requests at the entry point.

## Related Concepts

- **[Decorator Pattern](../../structural/2-decorator/)**: Decorator and Chain of Responsibility have similar structures. Decorators add responsibilities dynamically to an object, whereas Chain of Responsibility passes the request along a line of separate handlers.
- **[Composite Pattern](../../structural/composite/)**: Chain of Responsibility is often used in conjunction with Composite. In this case, when a leaf component receives a request, it can pass it up through the parent component chain.
