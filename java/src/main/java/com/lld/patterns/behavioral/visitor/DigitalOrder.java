package com.lld.patterns.behavioral.visitor;

public class DigitalOrder implements Order {
    private final int id;
    private final double amount;
    private final String email;

    public DigitalOrder(int id, double amount, String email) {
        this.id = id;
        this.amount = amount;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public double getAmount() {
        return amount;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public void accept(OrderVisitor visitor) {
        visitor.visit(this);
    }
}
