package com.lld.patterns.structural.facade;

public class NotificationService {
    private final INotificationSender sender;

    public NotificationService(INotificationSender sender) {
        this.sender = sender;
    }

    public void sendOrderConfirmation(String email, Order order) throws Exception {
        sender.send(email, "Order Confirmed", "Order #" + order.getId() + " — \u20B9" + order.getTotal());
    }

    public void sendOrderCancelled(String email, Order order) throws Exception {
        sender.send(email, "Order Cancelled", "Order #" + order.getId() + " has been cancelled");
    }

    public void sendRefundProcessed(String email, Order order, double amount) throws Exception {
        sender.send(email, "Refund Processed", "\u20B9" + amount + " refunded for order #" + order.getId());
    }
}
