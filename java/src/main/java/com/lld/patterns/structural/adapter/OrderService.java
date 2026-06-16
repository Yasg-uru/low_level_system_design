package com.lld.patterns.structural.adapter;

import java.util.List;

/**
 * Service speaking the standard domain language, calling external integrations via IPaymentGateway.
 */
public class OrderService {
    private final IOrderRepository orderRepo;
    private final IPaymentGateway gateway;
    private final INotificationService notifier;

    public OrderService(IOrderRepository orderRepo, IPaymentGateway gateway, INotificationService notifier) {
        this.orderRepo = orderRepo;
        this.gateway = gateway;
        this.notifier = notifier;
    }

    public Order checkout(String userId, List<CartItem> items) throws Exception {
        double total = 0;
        for (CartItem item : items) {
            total += item.getPrice() * item.getQuantity();
        }

        OrderMetadata metadata = new OrderMetadata(
            userId,
            "Order context bundle contains " + items.size() + " unique lines.",
            "rcpt_" + userId + "_" + System.currentTimeMillis()
        );

        PaymentOrder paymentOrder = gateway.createOrder(total, "INR", metadata);

        Order order = new Order(
            null,
            userId,
            items,
            total,
            paymentOrder.getId(),
            "pending"
        );

        return orderRepo.save(order);
    }

    public Order confirmPayment(String orderId, String paymentId) throws Exception {
        Order order = orderRepo.findById(orderId);
        if (order == null) {
            throw new IllegalArgumentException("Target record not found for Order tracking ID: " + orderId);
        }

        PaymentCapture capture = gateway.capturePayment(order.getPaymentOrderId(), paymentId);
        if (!capture.isSuccess()) {
            throw new IllegalStateException("Crucial core transaction capturing sequence rejected by vendor fallback.");
        }

        orderRepo.updateStatus(orderId, "confirmed");
        notifier.sendOrderConfirmation(order.getUserId(), order);

        return orderRepo.findById(orderId);
    }

    public void cancelOrder(String orderId, String paymentId) throws Exception {
        Order order = orderRepo.findById(orderId);
        if (order == null) {
            throw new IllegalArgumentException("Target record not found for Order tracking ID: " + orderId);
        }

        gateway.refund(paymentId, order.getTotal(), "Order cancelled by user");
        orderRepo.updateStatus(orderId, "cancelled");
        notifier.sendOrderCancelled(order.getUserId(), order);
    }
}
