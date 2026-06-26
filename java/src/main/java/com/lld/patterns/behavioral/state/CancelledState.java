package com.lld.patterns.behavioral.state;

import java.util.Collections;
import java.util.List;

/**
 * Terminal state representing a cancelled order.
 */
public class CancelledState implements IOrderState {
    @Override
    public void confirm(Order order) {
        throw new IllegalStateException("Cannot confirm a cancelled order.");
    }

    @Override
    public void ship(Order order) {
        throw new IllegalStateException("Cannot ship a cancelled order.");
    }

    @Override
    public void deliver(Order order) {
        throw new IllegalStateException("Cannot deliver a cancelled order.");
    }

    @Override
    public void cancel(Order order) {
        throw new IllegalStateException("Order is already cancelled.");
    }

    @Override
    public void requestReturn(Order order) {
        throw new IllegalStateException("Cannot request return for a cancelled order.");
    }

    @Override
    public String getStatus() {
        return "Cancelled";
    }

    @Override
    public List<String> getAllowedActions() {
        return Collections.emptyList();
    }
}
