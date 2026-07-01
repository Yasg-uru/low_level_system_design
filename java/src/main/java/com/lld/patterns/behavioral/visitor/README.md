# Visitor Design Pattern

## Overview

The **Visitor Pattern** is a behavioral design pattern that decouples an algorithm from the object structure it operates on. It allows you to add new operations to existing class hierarchies without changing their code.

It is particularly useful when you have a set of classes with distinct interfaces, and you need to perform operations across them that depend on their concrete types.

## Structure

1. **Visitor** (`OrderVisitor`): Defines the visit operations for each concrete element in the structure.
2. **Concrete Visitor** (`TaxVisitor`, `EmailVisitor`, `AuditVisitor`): Implements the operations declared by the Visitor.
3. **Element** (`Order`): Declares an `accept(Visitor)` method.
4. **Concrete Element** (`PhysicalOrder`, `DigitalOrder`): Implements the `accept(Visitor)` method, executing the visitor's operation using double dispatch.

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

## Double Dispatch

Double dispatch is the core mechanism of the Visitor pattern. In Java, method calls are dispatched dynamically based on the runtime type of the receiver object (single dispatch). Visitor achieves double dispatch by passing the visitor to the element (`order.accept(visitor)`), and then having the element pass itself back to the visitor (`visitor.visit(this)`). This resolves both the element type and the visitor type at runtime.

## Example Scenario: Order Processing

We have a collection of orders of different types (`PhysicalOrder`, `DigitalOrder`) and we want to perform operations like:
- Calculate Taxes (18% for physical, 12% for digital).
- Send notification emails (shipping address for physical, download link for digital).
- Generate audit logs.

### Java Implementation Files:

- **Element Interface**: [Order.java](file:///Users/apple/default/lld%20practice/java/src/main/java/com/lld/patterns/behavioral/visitor/Order.java)
- **Concrete Elements**:
  - [PhysicalOrder.java](file:///Users/apple/default/lld%20practice/java/src/main/java/com/lld/patterns/behavioral/visitor/PhysicalOrder.java)
  - [DigitalOrder.java](file:///Users/apple/default/lld%20practice/java/src/main/java/com/lld/patterns/behavioral/visitor/DigitalOrder.java)
- **Visitor Interface**: [OrderVisitor.java](file:///Users/apple/default/lld%20practice/java/src/main/java/com/lld/patterns/behavioral/visitor/OrderVisitor.java)
- **Concrete Visitors**:
  - [TaxVisitor.java](file:///Users/apple/default/lld%20practice/java/src/main/java/com/lld/patterns/behavioral/visitor/TaxVisitor.java)
  - [EmailVisitor.java](file:///Users/apple/default/lld%20practice/java/src/main/java/com/lld/patterns/behavioral/visitor/EmailVisitor.java)
  - [AuditVisitor.java](file:///Users/apple/default/lld%20practice/java/src/main/java/com/lld/patterns/behavioral/visitor/AuditVisitor.java)
- **Demo Runner**: [Main.java](file:///Users/apple/default/lld%20practice/java/src/main/java/com/lld/patterns/behavioral/visitor/Main.java)
