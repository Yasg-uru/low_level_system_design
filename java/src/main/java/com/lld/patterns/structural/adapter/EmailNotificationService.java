package com.lld.patterns.structural.adapter;

/**
 * Concrete domain notifier mock.
 */
public class EmailNotificationService implements INotificationService {
    @Override
    public void sendOrderConfirmation(String userId, Order order) {
        System.out.println("[Notification Engine] Dispatched confirmation email out to client: <" + userId + ">");
    }

    @Override
    public void sendOrderCancelled(String userId, Order order) {
        System.out.println("[Notification Engine] Dispatched cancellation receipts out to client: <" + userId + ">");
    }
}
