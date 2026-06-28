package com.lld.patterns.behavioral.iterator;

import java.time.LocalDateTime;

/**
 * Domain model representing an Order.
 */
public class Order {
    private final String id;
    private final double total;
    private final String status;
    private final LocalDateTime createdAt;

    public Order(String id, double total, String status, LocalDateTime createdAt) {
        this.id = id;
        this.total = total;
        this.status = status;
        this.createdAt = createdAt;
    }

    public String getId() {
        return id;
    }

    public double getTotal() {
        return total;
    }

    public String getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id='" + id + '\'' +
                ", total=" + total +
                ", status='" + status + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
