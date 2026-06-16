# Builder Design Pattern (Java)

## What is it?

The **Builder Pattern** is a creational design pattern that separates the construction of a complex object from its representation. It allows you to produce different types and representations of an object using the same construction code step-by-step.

In Java, the Builder Pattern is commonly implemented using a **Static Inner Class** inside the product class. This allows the builder to access the private constructor of the product class, guaranteeing immutability after construction while keeping construction logic closely coupled with the class it creates.

## Why use it?

- **Readable and Fluent API**: Provides a clean, chainable API (fluent interface) that improves code readability.
- **Immutability**: Allows you to gather parameters incrementally in a mutable builder, then construct an immutable, read-only final product (with `final` fields and no setters).
- **Prevents Telescoping Constructors**: Eliminates the need for multiple constructor overloads with different combinations of parameters.
- **Validation Before Construction**: Ensures the object is in a valid state before it is instantiated by running validation logic in the final `.build()` step.

## When to use?

- When building objects require many optional parameters or complex initialization logic (e.g., HTTP request builders, database queries, configuration objects).
- When you want the created product to be immutable.
- When the construction process must allow different representations for the object that's constructed.

## Common Mistakes

❌ **Bad approach (Telescoping Constructors or JavaBean Setters)**
```java
// Option A: Telescoping Constructors (Hard to read, easy to pass parameters in the wrong order)
public class HttpRequest {
    public HttpRequest(String url, String method) { ... }
    public HttpRequest(String url, String method, Map<String, String> headers) { ... }
    public HttpRequest(String url, String method, Map<String, String> headers, Object body, int timeout) { ... }
    // ... becomes impossible to maintain
}

// Option B: JavaBeans Pattern (Mutability makes it thread-unsafe, and can leave object in an incomplete state mid-execution)
HttpRequest request = new HttpRequest();
request.setUrl("https://api.example.com");
// What if thread switches here? Object is in an invalid state!
request.setMethod(HttpMethod.GET);
```

✅ **Good approach (Static Inner Class Builder)**
```java
HttpRequest request = new HttpRequest.Builder()
        .setUrl("https://api.example.com")
        .setMethod(HttpMethod.GET)
        .setTimeout(5000)
        .withRetries(3, 1000)
        .disableSslValidation()
        .build();
```

## Java Features Used

- **Static Inner Class**: Used to implement the Builder class inside `HttpRequest`.
- **Private Constructor**: Used in `HttpRequest` to prevent direct instantiation, enforcing construction only via the Builder.
- **Collections.unmodifiableMap**: Used to ensure that the headers map is truly immutable.
- **Base64 Encoder**: Used for standard Basic Auth token generation.

## Files in This Package

- `HttpMethod.java`: Enum representing available HTTP methods.
- `Response.java`: Representation of an HTTP response.
- `HttpRequest.java`: The final immutable product containing the static nested `Builder` class.
- `Main.java`: Bootstrap and runner showing fluent invocation.
- `examples/QueryBuilder.java`: SQL Query Builder example demonstrating step-by-step query construction.
