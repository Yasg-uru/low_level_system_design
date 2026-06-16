package com.lld.patterns.structural.adapter;

/**
 * Domain model representing a refund status.
 */
public class RefundResult {
    private final String refundId;
    private final double amount;
    private final String status; // "pending", "processed", "failed"

    public RefundResult(String refundId, double amount, String status) {
        this.refundId = refundId;
        this.amount = amount;
        this.status = status;
    }

    public String getRefundId() {
        return refundId;
    }

    public double getAmount() {
        return amount;
    }

    public String getStatus() {
        return status;
    }
}
