package com.lld.patterns.behavioral.state;

import java.util.List;

/**
 * Context class representing an Order whose behavior changes with its state.
 */
public class Order {
    private IOrderState state;

    public Order() {
        this.state = new PendingState();
    }

    public void setState(IOrderState state) {
        this.state = state;
    }

    public IOrderState getState() {
        return state;
    }

    public void confirm() {
        this.state.confirm(this);
    }

    public void ship() {
        this.state.ship(this);
    }

    public void deliver() {
        this.state.deliver(this);
    }

    public void cancel() {
        this.state.cancel(this);
    }

    public void requestReturn() {
        this.state.requestReturn(this);
    }

    public String getStatus() {
        return this.state.getStatus();
    }

    public List<String> getAllowedActions() {
        return this.state.getAllowedActions();
    }
}
