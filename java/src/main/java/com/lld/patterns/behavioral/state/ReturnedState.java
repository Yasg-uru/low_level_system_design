package com.lld.patterns.behavioral.state;

import java.util.Collections;
import java.util.List;

/**
 * Terminal state representing an order that has been returned.
 */
public class ReturnedState implements IOrderState {
    @Override
    public void confirm(Order order) {
        throw new IllegalStateException("Cannot confirm a returned order.");
    }

    @Override
    public void ship(Order order) {
        throw new IllegalStateException("Cannot ship a returned order.");
    }

    @Override
    public void deliver(Order order) {
        throw new IllegalStateException("Cannot deliver a returned order.");
    }

    @Override
    public void cancel(Order order) {
        throw new IllegalStateException("Cannot cancel a returned order.");
    }

    @Override
    public void requestReturn(Order order) {
        throw new IllegalStateException("Order is already returned.");
    }

    @Override
    public String getStatus() {
        return "Returned";
    }

    @Override
    public List<String> getAllowedActions() {
        return Collections.emptyList();
    }
}
