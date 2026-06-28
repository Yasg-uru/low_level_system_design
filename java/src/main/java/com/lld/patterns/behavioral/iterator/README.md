# Iterator Design Pattern - Java Implementation

## What is it?

The **Iterator Pattern** is a behavioral design pattern that allows traversing elements of a collection sequentially without exposing its underlying representation (such as lists, paginated database queries, or network streams).

This Java package implements two variants of the Iterator pattern:
1. **Synchronous Iterator (`IIterator`)**: A standard iterator pattern implementation for in-memory collections.
2. **Asynchronous Iterator (`IAsyncIterator`)**: An async, page-based iterator utilizing `CompletableFuture` to fetch data chunks asynchronously on-demand from a remote source.

## Why use it?

- **Decouples Collection Consumption**: Clients process elements sequentially using interfaces, completely unaware of whether elements reside in an array or are fetched lazily from a database.
- **Single Responsibility Principle**: Isolates the data access and traversal algorithms into dedicated classes rather than polluting business services or repository models.
- **Asynchronous Paging Abstraction**: Enables seamless async streaming over network-paginated services without blocking application threads.

## Implementation Details

In this Java package:
1. **`IIterator<T>`**: Synchronous traversal interface with `hasNext()`, `next()`, `reset()`, and `peek()`.
2. **`IAsyncIterator<T>`**: Asynchronous traversal interface returning `CompletableFuture` for `hasNext()` and `next()`.
3. **Concrete Iterators**:
   - `ArrayIterator<T>`: Traverses in-memory lists.
   - `FilteredIterator<T>`: Employs a Java `Predicate<T>` to lazily filter elements from an underlying iterator.
   - `PaginatedOrderIterator`: Fetches database pages of size $N$ asynchronously via the `OrderRepository` and streams single `Order` records to the client.
4. **`OrderReportService`**: Provides overloaded `generateReport` methods that consume both `IIterator<Order>` and `IAsyncIterator<Order>` to compile unified reports.
5. **`Main`**: Runs a demo combining array iteration, async paginated database fetches, and inline predicate filtering.

## Related Concepts

- **Decorator Pattern**: `FilteredIterator` decorates a standard `IIterator` to filter elements dynamically.
- **Strategy Pattern**: Different iterators represent different traversal strategies (in-memory vs. async paginated).
