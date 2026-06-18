package com.lld.patterns.structural.facade;

public class CartItem {
    private final String productId;
    private final int quantity;
    private final double price;

    public CartItem(String productId, int quantity, double price) {
        this.productId = productId;
        this.quantity = quantity;
        this.price = price;
    }

    public String getProductId() { return productId; }
    public int getQuantity() { return quantity; }
    public double getPrice() { return price; }
}
