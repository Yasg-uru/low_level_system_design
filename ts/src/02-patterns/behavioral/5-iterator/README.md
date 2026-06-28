# Iterator Design Pattern

## What is it?

The **Iterator Pattern** is a behavioral design pattern that allows you to traverse elements of a collection sequentially without exposing its underlying structure or representation (such as arrays, trees, hash tables, or paginated network streams). 

By using a standard interface, the consumer can retrieve elements one by one, remaining entirely decoupled from the concrete data structure and the traversal mechanics.

## Why use it?

- **Decouples Traversal from Storage**: The client doesn't need to know if it is iterating over an in-memory array, a linked list, or fetching pages asynchronously from a database.
- **Single Responsibility Principle**: The collection is only responsible for storing data, while the Iterator is responsible for traversal.
- **Multiple Concurrent Traversals**: Since each Iterator maintains its own traversal state (like `index` or `currentPage`), multiple iterators can traverse the same collection simultaneously.
- **Supports Async Iteration**: Ideal for streaming large datasets over a network where pages of data are loaded lazily on-demand.

## When to use?

- When you have a complex data structure (e.g., a tree, graph, or paginated database stream) and you want to hide its implementation details from the client.
- When you want to traverse different data structures using the same client code (polymorphic iteration).
- When traversing requires complex logic, such as filtering, sorting, or fetching data in chunks.

## Common Mistakes

❌ **Bad approach (Exposing representation and hardcoding traversal)**
If a client needs to generate a report, exposing the raw array directly forces the client to know the structure. If the data source changes to a paginated database, all client code must be rewritten to handle page offsets and async queries.
```typescript
class OrderService {
  private orders: Order[] = [];

  getRawOrders(): Order[] {
    return this.orders; // Exposes underlying array
  }
}

class ReportService {
  generateReport(service: OrderService) {
    // Tightly coupled to in-memory array implementation
    const orders = service.getRawOrders();
    for (let i = 0; i < orders.length; i++) {
      console.log(orders[i].id);
    }
  }
}
```

✅ **Good approach (Using Iterator Abstraction)**
Using an iterator contract allows the client to consume elements sequentially, regardless of whether the source is a local array or a paginated network stream.
```typescript
interface IIterator<T> {
  hasNext(): boolean;
  next(): T;
}

class ArrayIterator<T> implements IIterator<T> {
  private index = 0;
  constructor(private items: T[]) {}

  hasNext(): boolean {
    return this.index < this.items.length;
  }

  next(): T {
    return this.items[this.index++];
  }
}

class ReportService {
  generateReport(iterator: IIterator<Order>) {
    while (iterator.hasNext()) {
      const order = iterator.next();
      console.log(order.id);
    }
  }
}
```

## Key Points

- **Iterator Interface (`IIterator<T>`)**: Declares the operations necessary for traversing a collection (`hasNext()`, `next()`, `reset()`, `peek()`).
- **Concrete Iterator (`ArrayIterator`, `FilteredIterator`)**: Implements the traversal algorithm and tracks the current position.
- **Asynchronous Iterator (`IAsyncIterator<T>`)**: Uses promises to support lazy-loading elements sequentially from remote/paginated sources.
- **Decorator/Wrapper Iterators**: Iterators can wrap other iterators to add behavior like filtering (`FilteredIterator`) or logging on-the-fly.

## Related Concepts

- **[Observer Pattern](../1-observer/)**: Iterators are pull-based (the consumer asks for the next item), whereas Observers are push-based (the producer pushes updates to listeners).
- **[Decorator Pattern](../../structural/2-decorator/)**: A `FilteredIterator` acts like a decorator by wrapping a standard iterator to add dynamic predicate filters without modifying the original collection.
