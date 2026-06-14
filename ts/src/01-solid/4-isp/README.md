# Interface Segregation Principle (ISP)

## What is it?

Clients should not be forced to depend on interfaces they don't use. It's better to have many specific interfaces than one general-purpose interface.

## Why use it?

- **Flexible Design**: Classes implement only needed functionality
- **Cleaner Code**: No dummy implementations throwing errors
- **Better Testing**: Mock only what's needed
- **Easier Changes**: Modifying one interface doesn't affect unrelated classes

## When to use?

- When an interface has many methods
- When some implementations don't need all methods
- When you want to avoid "fat interfaces"
- Building libraries where clients use different features

## Signs of ISP Violation

- Interface with 5+ methods
- Some implementations throw "not implemented" errors
- Implementations have empty methods
- Clients use only some methods of an interface

## Common Mistakes

❌ **One large interface**
```typescript
interface IAnimal {
  eat(): void;
  sleep(): void;
  fly(): void;   // What if animal can't fly?
  swim(): void;  // What if animal can't swim?
}
```

✅ **Segregated interfaces**
```typescript
interface IEatable { eat(): void; }
interface ISleepable { sleep(): void; }
interface IFlyable { fly(): void; }
interface ISwimmable { swim(): void; }

class Bird implements IEatable, ISleepable, IFlyable { }
class Fish implements IEatable, ISleepable, ISwimmable { }
class Dog implements IEatable, ISleepable { }
```

## How to Apply ISP

1. **Identify distinct capabilities**: eating, flying, swimming
2. **Create small interfaces**: one capability per interface
3. **Implement selectively**: classes implement only needed interfaces
4. **Use composition**: combine interfaces for complex behaviors

## Key Points

- Small, focused interfaces are better than large ones
- Role-based interfaces (IFlyable, ISwimmable)
- Clients depend only on what they use
- Mix and match interfaces as needed

## Related Concepts

- **Single Responsibility**: Each interface should have one job
- **Composition**: Combining multiple small interfaces
- **Abstraction**: Hiding unneeded complexity
