package com.lld.patterns.behavioral.command;

import java.util.List;

/**
 * Represents a customer order.
 */
public class Order {
    private final String id;
    private final String userId;
    private final List<CartItem> items;
    private double total;
    private OrderStatus status;

    public Order(String id, String userId, List<CartItem> items, double total, OrderStatus status) {
        this.id = id;
        this.userId = userId;
        this.items = items;
        this.total = total;
        this.status = status;
    }

    public String getId() {
        return id;
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

    public void setTotal(double total) {
        this.total = total;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }
}
