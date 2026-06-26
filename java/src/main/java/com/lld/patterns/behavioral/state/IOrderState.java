package com.lld.patterns.behavioral.state;

import java.util.List;

/**
 * State interface defining all possible actions on an Order.
 */
public interface IOrderState {
    void confirm(Order order);
    void ship(Order order);
    void deliver(Order order);
    void cancel(Order order);
    void requestReturn(Order order);
    String getStatus();
    List<String> getAllowedActions();
}
