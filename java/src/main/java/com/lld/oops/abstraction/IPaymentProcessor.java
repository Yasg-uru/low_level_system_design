package com.lld.oops.abstraction;

/**
 * Interface demonstrating abstraction through interfaces.
 * Pure contract - no implementation.
 */
public interface IPaymentProcessor {
    boolean processPayment(double amount);
    boolean refund(String transactionId);
    String getTransactionStatus(String transactionId);
}
