# Prototype Design Pattern

## What is it?

The **Prototype Pattern** is a creational design pattern that allows cloning existing objects without coupling your code to their specific concrete classes. Instead of creating a new instance from scratch using a constructor (which might involve expensive operations like fetching database records or reading config files), you clone an existing archetypal instance (the prototype).

## Why use it?

- **Reduced Construction Cost**: Avoids the performance overhead of complex initialization logic (e.g., parsing HTML templates, reading file attachments) by copying pre-instantiated memory blocks.
- **Decoupled Client Code**: Clients do not need to know the concrete configuration classes or complex constructor arguments; they simply request a copy of a baseline prototype and customize it.
- **Simplifies Object State Combinations**: Rather than maintaining a complex hierarchy of subclasses representing various state configurations, you can store pre-configured instances in a registry and clone them.

## When to use?

- When object creation is computationally expensive or resource-intensive.
- When you want to isolate the client from the concrete implementation details of the object being copied.
- When your system needs to produce objects that only differ slightly in state (e.g., email templates with different client names, game entities with varied stats).

## Common Mistakes

❌ **Bad approach (Shallow copy of nested objects)**
```typescript
// Directly using Object.assign or spread operators fails for nested structures
const originalTemplate = {
  subject: "Welcome",
  metadata: { priority: "high", tags: ["auth"] },
  attachments: [{ filename: "rules.pdf", data: Buffer.from([1, 2, 3]) }]
};

// Modifying the copy changes the original!
const cloneTemplate = { ...originalTemplate };
cloneTemplate.metadata.priority = "low"; // Mutates originalTemplate.metadata.priority!
cloneTemplate.attachments[0].filename = "terms.pdf"; // Mutates originalTemplate attachments!
```

✅ **Good approach (Deep copy via custom `clone()` method)**
```typescript
export interface IPrototype<T> {
  clone(): T;
}

export class EmailTemplate implements IPrototype<EmailTemplate> {
  private subject: string;
  private metadata: Record<string, unknown>;
  private attachments: EmailAttachment[];

  constructor(config: EmailTemplateConfig) {
    this.subject = config.subject;
    this.metadata = config.metadata;
    this.attachments = config.attachments;
  }

  public clone(): EmailTemplate {
    return new EmailTemplate({
      subject: this.subject,
      // Properly deep copying nested structures
      metadata: this.metadata ? JSON.parse(JSON.stringify(this.metadata)) : {},
      attachments: this.attachments ? this.attachments.map(att => ({ ...att, data: Buffer.from(att.data) })) : []
    });
  }
}
```

## Key Points

- **Deep Copy**: Ensure all nested structures, arrays, and buffers are copied recursively. Sharing references defeats the purpose of the prototype pattern and leaks state mutation side effects.
- **Prototype Registry**: Often combined with a Registry pattern, which maintains a cache/catalog of pre-configured prototypes.
- **Fluent APIs**: Typically used in combination with chaining/mutation step methods (e.g., `.withSubject()`, `.withHeader()`) to dynamically configure the cloned object.

## Related Concepts

- **[Factory Method](../2-factory-method/)**: Often starts as Factory Method and evolves toward Prototype when creation becomes dynamic or configuration-heavy.
- **[Abstract Factory](../3-abstract-factory/)**: Can store a set of prototypes to clone and return instead of instantiating new objects directly.
- **Mementos**: While Prototype focuses on copying state to a new active object, Mementos focus on capturing snapshots to restore state later.
