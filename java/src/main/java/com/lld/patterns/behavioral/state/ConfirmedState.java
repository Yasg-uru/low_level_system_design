package com.lld.patterns.behavioral.state;

import java.util.Arrays;
import java.util.List;

/**
 * State representing a confirmed order that is ready for shipping.
 */
public class ConfirmedState implements IOrderState {
    @Override
    public void confirm(Order order) {
        throw new IllegalStateException("Order is already confirmed.");
    }

    @Override
    public void ship(Order order) {
        System.out.println("-> Order passed to transit delivery channels.");
        order.setState(new ShippedState());
    }

    @Override
    public void deliver(Order order) {
        throw new IllegalStateException("Cannot deliver a confirmed order. Ship it first.");
    }

    @Override
    public void cancel(Order order) {
        System.out.println("-> Order cancelled from confirmed state.");
        order.setState(new CancelledState());
    }

    @Override
    public void requestReturn(Order order) {
        throw new IllegalStateException("Cannot request return for a confirmed order.");
    }

    @Override
    public String getStatus() {
        return "Confirmed";
    }

    @Override
    public List<String> getAllowedActions() {
        return Arrays.asList("ship", "cancel");
    }
}
