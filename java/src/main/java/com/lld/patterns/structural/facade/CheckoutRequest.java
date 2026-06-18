package com.lld.patterns.structural.facade;

import java.util.List;

public class CheckoutRequest {
    private final String userId;
    private final List<CartItem> items;

    public CheckoutRequest(String userId, List<CartItem> items) {
        this.userId = userId;
        this.items = items;
    }

    public String getUserId() { return userId; }
    public List<CartItem> getItems() { return items; }
}
