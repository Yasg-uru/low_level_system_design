package com.lld.patterns.behavioral.interpreter;

public class OrderContext {
    private final String status;
    private final double total;
    private final String type;
    private final String userId;

    public OrderContext(String status, double total, String type, String userId) {
        this.status = status;
        this.total = total;
        this.type = type;
        this.userId = userId;
    }

    public String getStatus() {
        return status;
    }

    public double getTotal() {
        return total;
    }

    public String getType() {
        return type;
    }

    public String getUserId() {
        return userId;
    }
}