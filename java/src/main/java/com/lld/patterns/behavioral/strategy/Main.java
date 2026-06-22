package com.lld.patterns.behavioral.strategy;

/**
 * Driver client demonstrating the Strategy Pattern in Java.
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("=============================================================");
        System.out.println("=== Strategy Design Pattern: Payment Processing (Java)   ===");
        System.out.println("=============================================================\n");

        // Initialized with Credit Card Strategy
        PaymentContext context = new PaymentContext(new CreditCardPaymentStrategy());
        context.pay(100.00);

        // Dynamically change strategy to PayPal
        context.setStrategy(new PayPalPaymentStrategy());
        context.pay(200.00);

        // Dynamically change strategy to Bitcoin
        context.setStrategy(new BitcoinPaymentStrategy());
        context.pay(300.00);
    }
}
