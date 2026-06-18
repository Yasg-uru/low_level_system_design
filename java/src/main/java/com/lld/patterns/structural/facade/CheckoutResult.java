package com.lld.patterns.structural.facade;

public class CheckoutResult {
    private final boolean success;
    private final Order order;
    private final String error;

    public CheckoutResult(boolean success, Order order, String error) {
        this.success = success;
        this.order = order;
        this.error = error;
    }

    public static CheckoutResult success(Order order) {
        return new CheckoutResult(true, order, null);
    }

    public static CheckoutResult failure(String error) {
        return new CheckoutResult(false, null, error);
    }

    public boolean isSuccess() { return success; }
    public Order getOrder() { return order; }
    public String getError() { return error; }
}
