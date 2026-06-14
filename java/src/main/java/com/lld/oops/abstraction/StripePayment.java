package com.lld.oops.abstraction;

/**
 * Concrete implementation of IPaymentProcessor.
 * Provides Stripe-specific payment processing.
 */
public class StripePayment implements IPaymentProcessor {

    @Override
    public boolean processPayment(double amount) {
        try {
            System.out.println("Processing Stripe payment: $" + amount);
            Thread.sleep(100);  // Simulate API call
            System.out.println("Stripe payment successful");
            return true;
        } catch (InterruptedException e) {
            System.err.println("Stripe payment failed: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean refund(String transactionId) {
        System.out.println("Refunding Stripe transaction: " + transactionId);
        return true;
    }

    @Override
    public String getTransactionStatus(String transactionId) {
        return "Stripe transaction " + transactionId + ": completed";
    }
}
