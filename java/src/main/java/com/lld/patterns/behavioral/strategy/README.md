# Strategy Design Pattern - Java

## Overview

The **Strategy Design Pattern** is a behavioral design pattern that allows defining a family of algorithms, encapsulating each one, and making them interchangeable. The strategy pattern lets the algorithm vary independently from the clients that use it.

In this implementation, the payment processing engine represents the context, which delegates payment executions to interchangeable strategy implementations (`CreditCardPaymentStrategy`, `PayPalPaymentStrategy`, `BitcoinPaymentStrategy`).

## Class Structure

- **`IPaymentStrategy`**: The strategy interface that defines the contract (`processPayment`) common to all algorithms.
- **`CreditCardPaymentStrategy` / `PayPalPaymentStrategy` / `BitcoinPaymentStrategy`**: Concrete strategies implementing specific payment algorithms.
- **`PaymentContext`**: The context class that maintains a reference to a strategy object and uses it to delegate payment execution.

## Benefits

- **Avoid Conditional Statements**: Avoids using nested `if-else` or `switch` statements to select a payment handler.
- **Single Responsibility Principle**: Individual payment logic is separated into standalone classes.
- **Open/Closed Principle**: New payment types can be supported by writing a new implementation of `IPaymentStrategy` without editing `PaymentContext`.
- **Runtime Swappability**: Changing the payment algorithm dynamically at runtime is supported through a setter (`setStrategy`).

## Usage Example

```java
// Initialize the payment context with credit card processing
PaymentContext context = new PaymentContext(new CreditCardPaymentStrategy());
context.pay(100.00);

// Swap behavior dynamically to PayPal
context.setStrategy(new PayPalPaymentStrategy());
context.pay(200.00);
```
