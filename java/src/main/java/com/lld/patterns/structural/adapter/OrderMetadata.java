package com.lld.patterns.structural.adapter;

/**
 * Metadata associated with payment orders.
 */
public class OrderMetadata {
    private final String userId;
    private final String description;
    private final String receiptId;

    public OrderMetadata(String userId, String description, String receiptId) {
        this.userId = userId;
        this.description = description;
        this.receiptId = receiptId;
    }

    public String getUserId() {
        return userId;
    }

    public String getDescription() {
        return description;
    }

    public String getReceiptId() {
        return receiptId;
    }
}
