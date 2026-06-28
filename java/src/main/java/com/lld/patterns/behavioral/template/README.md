# Template Method Design Pattern - Java Implementation

## What is it?

The **Template Method Pattern** is a behavioral design pattern that defines the sequential skeleton of an algorithm in an abstract superclass, letting subclasses override specific steps without modifying the overarching structure.

This package implements a user registration workflow. The base class coordinates validation, creation, notifications, and logging, while subclasses define specific mechanics for standard customers versus system administrators.

## Why use it?

- **Enforces Workflow Invariance**: By declaring the template method (`registerUser`) as `final`, subclasses are blocked from changing the sequence of steps (Validation -> Creation -> Email -> Log).
- **Reduces Duplicate Code**: Consolidates common tasks (like input parameter validation and execution logging hooks) inside the base class.
- **Asynchronous Flow Orchestration**: Leverages Java's `CompletableFuture` to chain asynchronous steps non-blockingly while maintaining strict execution ordering.

## Implementation Details

In this Java package:
1. **`User`**: Value object holding credentials and contact details.
2. **`UserRegistrationTemplate`**: Abstract superclass containing the `final` template method `registerUser(User userData)` and validation helpers.
3. **Concrete Subclasses**:
   - `CustomerUserRegistration`: Implements customer-specific row saving and welcomes.
   - `AdminUserRegistration`: Implements system administrative record creation and security alerts.
4. **`Main`**: Driver program that registers a customer and an administrator sequentially.

## Related Concepts

- **Factory Method Pattern**: The abstract step `createUser` acts as a factory method called by the registration template.
- **Strategy Pattern**: Strategy swaps out entire interchangeable algorithms using composition, whereas Template Method modifies parts of an algorithm using inheritance.
