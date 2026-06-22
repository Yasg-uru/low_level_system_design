package com.lld.patterns.behavioral.strategy;

/**
 * Concrete Strategy for Bitcoin payments.
 */
public class BitcoinPaymentStrategy implements IPaymentStrategy {
    @Override
    public void processPayment(double amount) {
        System.out.printf("Processing Bitcoin payment of $%.2f%n", amount);
    }
}
