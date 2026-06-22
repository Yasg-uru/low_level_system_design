package com.lld.patterns.behavioral.strategy;

/**
 * Concrete Strategy for PayPal payments.
 */
public class PayPalPaymentStrategy implements IPaymentStrategy {
    @Override
    public void processPayment(double amount) {
        System.out.printf("Processing PayPal payment of $%.2f%n", amount);
    }
}
