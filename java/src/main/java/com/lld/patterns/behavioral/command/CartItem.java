package com.lld.patterns.behavioral.command;

/**
 * Represents an item in a shopping cart.
 */
public class CartItem {
    private final String id;
    private final int quantity;

    public CartItem(String id, int quantity) {
        this.id = id;
        this.quantity = quantity;
    }

    public String getId() {
        return id;
    }

    public int getQuantity() {
        return quantity;
    }
}
