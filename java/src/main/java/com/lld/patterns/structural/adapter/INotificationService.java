package com.lld.patterns.structural.adapter;

/**
 * Domain notification interface.
 */
public interface INotificationService {
    void sendOrderConfirmation(String userId, Order order);
    void sendOrderCancelled(String userId, Order order);
}
