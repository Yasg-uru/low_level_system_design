package com.lld.patterns.structural.facade;

import java.util.List;

public class CreateOrderDTO {
    private final String userId;
    private final List<CartItem> items;
    private final double total;
    private final String paymentId;

    public CreateOrderDTO(String userId, List<CartItem> items, double total, String paymentId) {
        this.userId = userId;
        this.items = items;
        this.total = total;
        this.paymentId = paymentId;
    }

    public String getUserId() { return userId; }
    public List<CartItem> getItems() { return items; }
    public double getTotal() { return total; }
    public String getPaymentId() { return paymentId; }
}
