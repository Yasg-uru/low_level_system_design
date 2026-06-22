package com.lld.patterns.behavioral.strategy;

/**
 * Context class that holds a reference to a Strategy and delegates task execution to it.
 */
public class PaymentContext {
    private IPaymentStrategy strategy;

    /**
     * Initial constructor accepting a default strategy.
     */
    public PaymentContext(IPaymentStrategy strategy) {
        this.strategy = strategy;
    }

    /**
     * Dynamically update strategy at runtime.
     */
    public void setStrategy(IPaymentStrategy strategy) {
        this.strategy = strategy;
    }

    /**
     * Execute the payment delegation.
     */
    public void pay(double amount) {
        if (this.strategy == null) {
            throw new IllegalStateException("Payment strategy is not set.");
        }
        this.strategy.processPayment(amount);
    }
}
