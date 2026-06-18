package com.lld.patterns.structural.facade;

public class PaymentResult {
    private final boolean success;
    private final String transactionId;
    private final double amount;

    public PaymentResult(boolean success, String transactionId, double amount) {
        this.success = success;
        this.transactionId = transactionId;
        this.amount = amount;
    }

    public boolean isSuccess() { return success; }
    public String getTransactionId() { return transactionId; }
    public double getAmount() { return amount; }
}
