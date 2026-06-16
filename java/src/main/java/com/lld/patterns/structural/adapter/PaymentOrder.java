package com.lld.patterns.structural.adapter;

import java.util.Date;

/**
 * Domain model representing a created order on the gateway.
 */
public class PaymentOrder {
    private final String id;
    private final double amount;
    private final String currency;
    private final String status; // e.g. "created", "attempted", "paid"
    private final Date createdAt;

    public PaymentOrder(String id, double amount, String currency, String status, Date createdAt) {
        this.id = id;
        this.amount = amount;
        this.currency = currency;
        this.status = status;
        this.createdAt = createdAt;
    }

    public String getId() {
        return id;
    }

    public double getAmount() {
        return amount;
    }

    public String getCurrency() {
        return currency;
    }

    public String getStatus() {
        return status;
    }

    public Date getCreatedAt() {
        return createdAt;
    }
}
