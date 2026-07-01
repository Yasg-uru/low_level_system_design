package com.lld.patterns.behavioral.visitor;

public class PhysicalOrder implements Order {
    private final int id;
    private final double amount;
    private final String shippingAddress;

    public PhysicalOrder(int id, double amount, String shippingAddress) {
        this.id = id;
        this.amount = amount;
        this.shippingAddress = shippingAddress;
    }

    public int getId() {
        return id;
    }

    public double getAmount() {
        return amount;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    @Override
    public void accept(OrderVisitor visitor) {
        visitor.visit(this);
    }
}
