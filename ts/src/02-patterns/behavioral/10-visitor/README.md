# Visitor Design Pattern

## What is it?

The **Visitor Pattern** is a behavioral design pattern that allows you to separate algorithms from the objects on which they operate. It lets you define new operations on an object structure without modifying the structure itself.

It uses a technique called **Double Dispatch** to resolve the correct method call dynamically at runtime.

## Why use it?

- **Open/Closed Principle**: You can introduce new operations that work with these objects without modifying the source code of the objects.
- **Single Responsibility Principle**: You can gather related behaviors into a single class (the Visitor) rather than spreading them across multiple element classes.
- **Clean Element Classes**: Element classes remain lightweight and focused on their core data/responsibility rather than diverse operational procedures (e.g., serialization, auditing, tax calculations).

## Structure

1. **Visitor** (`OrderVisitor`): Declares a set of visiting methods for each class of concrete elements.
2. **Concrete Visitor** (`TaxVisitor`, `EmailVisitor`, `AuditVisitor`): Implements the operations declared by the Visitor.
3. **Element** (`Order`): Declares an `accept` method that takes a visitor as an argument.
4. **Concrete Element** (`PhysicalOrder`, `DigitalOrder`): Implements the `accept` method, redirecting execution to the matching visitor method via double dispatch.

```
                  +-------------------+
                  |      Visitor      |
                  +-------------------+
                  | +visitElementA()  |
                  | +visitElementB()  |
                  +-------------------+
                            ^
                            | implements
             +--------------+--------------+
             |                             |
    +-----------------+           +-----------------+
    | ConcreteVisitor1|           | ConcreteVisitor2|
    +-----------------+           +-----------------+
             |                             |
             +--------------+--------------+
                            |
                            | calls methods on
                            v
                  +-------------------+
                  |      Element      |
                  +-------------------+
                  | +accept(Visitor)  |
                  +-------------------+
                            ^
                            | implements
             +--------------+--------------+
             |                             |
    +-----------------+           +-----------------+
    | ConcreteElementA|           | ConcreteElementB|
    +-----------------+           +-----------------+
    | +accept(v) {    |           | +accept(v) {    |
    |   v.visitA(this)|           |   v.visitB(this)|
    | }               |           | }               |
    +-----------------+           +-----------------+
```

## Example Scenario: Order Processing System

For the TypeScript implementation, see [basic.ts](file:///Users/apple/default/lld%20practice/ts/src/02-patterns/behavioral/10-visitor/basic.ts).

In this example, we process different types of orders without modifying the order structures:
- **`Order`** is the element interface.
- **`PhysicalOrder`** and **`DigitalOrder`** are concrete elements.
- **`OrderVisitor`** defines operations for tax calculation, email delivery, and auditing.
- Visitors like **`TaxVisitor`**, **`EmailVisitor`**, and **`AuditVisitor`** perform their respective tasks cleanly over a collection of orders.
