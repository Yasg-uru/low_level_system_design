package com.lld.patterns.structural.adapter;

import java.util.List;

/**
 * Domain entity representing an customer purchase order.
 */
public class Order {
    private String id;
    private final String userId;
    private final List<CartItem> items;
    private final double total;
    private final String paymentOrderId;
    private String status; // "pending", "confirmed", "cancelled"

    public Order(String id, String userId, List<CartItem> items, double total, String paymentOrderId, String status) {
        this.id = id;
        this.userId = userId;
        this.items = items;
        this.total = total;
        this.paymentOrderId = paymentOrderId;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public List<CartItem> getItems() {
        return items;
    }

    public double getTotal() {
        return total;
    }

    public String getPaymentOrderId() {
        return paymentOrderId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
