# Builder Design Pattern

## What is it?

The **Builder Pattern** is a creational design pattern that separates the construction of a complex object from its representation. It allows you to produce different types and representations of an object using the same construction code step-by-step.

## Why use it?

- **Readable and Fluent API**: Provides a clean, chainable API (fluent interface) that improves code readability when creating complex configurations.
- **Immutability**: Allows you to gather parameters incrementally in a mutable builder, then construct an immutable, read-only final product.
- **Prevents Telescoping Constructors**: Eliminates the need for multiple constructor variations (overloads) with different combinations of parameters.
- **Validation Before Construction**: Ensures the object is in a valid state before it is instantiated by running validation logic in the final `.build()` step.

## When to use?

- When building objects require many optional parameters or complex initialization logic (e.g., HTTP request builders, database queries, config objects).
- When you want the created product to be immutable.
- When the construction process must allow different representations for the object that's constructed.

## Common Mistakes

❌ **Bad approach (Telescoping Constructor / Huge Parameter Object)**
```typescript
// Client code has to supply a huge configuration object, or constructor has way too many arguments.
// Easy to pass incorrect types/arguments or get lost in optional flags.
class HttpRequest {
  constructor(
    public url: string,
    public method: HttpMethod,
    public headers?: Record<string, string>,
    public body?: unknown,
    public timeout?: number,
    public retries?: number,
    public retryDelay?: number,
    public followRedirects?: boolean,
    public maxRedirects?: number,
    public validateSsl?: boolean
  ) {}
}

// Usage becomes highly unreadable and hard to manage
const request = new HttpRequest(
  "https://api.example.com",
  "GET",
  undefined,
  undefined,
  5000,
  3,
  1000,
  true,
  undefined,
  false
);
```

✅ **Good approach (Fluent Builder)**
```typescript
const request = new HttpRequestBuilder()
  .setUrl("https://api.example.com")
  .setMethod("GET")
  .setTimeout(5000)
  .withRetries(3, 1000)
  .disableSslValidation()
  .build();
```

## Differences from Other Patterns

| Pattern | Purpose | Main Focus |
|---|---|---|
| **Builder** | Constructs complex objects step-by-step | Multi-step creation and configuration |
| **Factory Method** | Creates objects in a single call | Creation of a single product type via subclassing |
| **Abstract Factory** | Creates families of related products | Consistency across families of objects |

## Key Points

- Builder is about step-by-step creation, whereas Factory is about one-shot creation.
- A **Director** class can optionally be introduced to define common construction recipes/routines.
- Returns `this` in setters to enable method chaining.
- Validation should be placed in the `.build()` method before returning the product.

## Related Concepts

- **[Factory Method](../2-factory-method/)**: Simple creation counterpart.
- **Fluent Interface**: The chaining technique used in modern builders.
- **Immutable Object**: The typical product of a builder pattern.
