package com.lld.patterns.structural.facade;

import java.util.Date;
import java.util.List;

public class Order {
    private final String id;
    private final String userId;
    private final List<CartItem> items;
    private final double total;
    private final String paymentId;
    private final String status; // "confirmed" or "cancelled"
    private final Date createdAt;

    public Order(String id, String userId, List<CartItem> items, double total, String paymentId, String status, Date createdAt) {
        this.id = id;
        this.userId = userId;
        this.items = items;
        this.total = total;
        this.paymentId = paymentId;
        this.status = status;
        this.createdAt = createdAt;
    }

    public String getId() { return id; }
    public String getUserId() { return userId; }
    public List<CartItem> getItems() { return items; }
    public double getTotal() { return total; }
    public String getPaymentId() { return paymentId; }
    public String getStatus() { return status; }
    public Date getCreatedAt() { return createdAt; }
}
