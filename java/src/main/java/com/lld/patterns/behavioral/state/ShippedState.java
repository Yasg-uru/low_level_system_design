package com.lld.patterns.behavioral.state;

import java.util.Arrays;
import java.util.List;

/**
 * State representing an order that has been shipped and is in transit.
 */
public class ShippedState implements IOrderState {
    @Override
    public void confirm(Order order) {
        throw new IllegalStateException("Order is already confirmed and shipped.");
    }

    @Override
    public void ship(Order order) {
        throw new IllegalStateException("Order is already shipped.");
    }

    @Override
    public void deliver(Order order) {
        System.out.println("-> Order successfully handed over to destination user.");
        order.setState(new DeliveredState());
    }

    @Override
    public void cancel(Order order) {
        throw new IllegalStateException("Cannot cancel a shipped order.");
    }

    @Override
    public void requestReturn(Order order) {
        throw new IllegalStateException("Cannot request return for a shipped order. Deliver it first.");
    }

    @Override
    public String getStatus() {
        return "Shipped";
    }

    @Override
    public List<String> getAllowedActions() {
        return Arrays.asList("deliver");
    }
}
