package com.lld.patterns.structural.adapter;

import java.util.Date;

/**
 * Domain model representing transaction capture response.
 */
public class PaymentCapture {
    private final boolean success;
    private final String transactionId;
    private final Date capturedAt;

    public PaymentCapture(boolean success, String transactionId, Date capturedAt) {
        this.success = success;
        this.transactionId = transactionId;
        this.capturedAt = capturedAt;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public Date getCapturedAt() {
        return capturedAt;
    }
}
