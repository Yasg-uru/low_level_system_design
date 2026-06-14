package com.lld.oops.abstraction;

/**
 * Another concrete implementation of IPaymentProcessor.
 * Provides PayPal-specific payment processing.
 */
public class PayPalPayment implements IPaymentProcessor {

    @Override
    public boolean processPayment(double amount) {
        try {
            System.out.println("Processing PayPal payment: $" + amount);
            Thread.sleep(150);  // Simulate API call
            System.out.println("PayPal payment successful");
            return true;
        } catch (InterruptedException e) {
            System.err.println("PayPal payment failed: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean refund(String transactionId) {
        System.out.println("Refunding PayPal transaction: " + transactionId);
        return true;
    }

    @Override
    public String getTransactionStatus(String transactionId) {
        return "PayPal transaction " + transactionId + ": pending";
    }
}
