package com.lld.patterns.behavioral.strategy;

/**
 * Concrete Strategy for Credit Card payments.
 */
public class CreditCardPaymentStrategy implements IPaymentStrategy {
    @Override
    public void processPayment(double amount) {
        System.out.printf("Processing Credit Card payment of $%.2f%n", amount);
    }
}
