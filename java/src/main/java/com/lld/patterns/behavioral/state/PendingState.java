package com.lld.patterns.behavioral.state;

import java.util.Arrays;
import java.util.List;

/**
 * State representing an order that is pending confirmation.
 */
public class PendingState implements IOrderState {
    @Override
    public void confirm(Order order) {
        System.out.println("-> Order confirmed successfully.");
        order.setState(new ConfirmedState());
    }

    @Override
    public void ship(Order order) {
        throw new IllegalStateException("Cannot ship a pending order. Confirm it first.");
    }

    @Override
    public void deliver(Order order) {
        throw new IllegalStateException("Cannot deliver a pending order. Confirm it first.");
    }

    @Override
    public void cancel(Order order) {
        System.out.println("-> Order cancelled from pending state.");
        order.setState(new CancelledState());
    }

    @Override
    public void requestReturn(Order order) {
        throw new IllegalStateException("Cannot request return for a pending order.");
    }

    @Override
    public String getStatus() {
        return "Pending";
    }

    @Override
    public List<String> getAllowedActions() {
        return Arrays.asList("confirm", "cancel");
    }
}
