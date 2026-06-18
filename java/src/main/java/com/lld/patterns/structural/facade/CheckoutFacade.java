package com.lld.patterns.structural.facade;

import java.util.HashMap;
import java.util.Map;

public class CheckoutFacade {
    private final OrderService orderService;
    private final PaymentService paymentService;
    private final InventoryService inventoryService;
    private final NotificationService notificationService;
    private final AuditService auditService;
    private final IUserRepository userRepo;

    public CheckoutFacade(
            OrderService orderService,
            PaymentService paymentService,
            InventoryService inventoryService,
            NotificationService notificationService,
            AuditService auditService,
            IUserRepository userRepo) {
        this.orderService = orderService;
        this.paymentService = paymentService;
        this.inventoryService = inventoryService;
        this.notificationService = notificationService;
        this.auditService = auditService;
        this.userRepo = userRepo;
    }

    public CheckoutResult checkout(CheckoutRequest request) {
        String userId = request.getUserId();
        double total = request.getItems().stream().mapToDouble(i -> i.getPrice() * i.getQuantity()).sum();
        User user = userRepo.findById(userId);

        if (user == null) {
            return CheckoutResult.failure("User not found");
        }

        // Step 1: Reserve inventory
        InventoryService.ReservationResult reservation = inventoryService.reserveItems(request.getItems());
        if (!reservation.isSuccess()) {
            return CheckoutResult.failure(reservation.getReason());
        }

        // Step 2: Charge payment — rollback inventory on structural exception or denial
        PaymentResult payment;
        try {
            payment = paymentService.charge(total, userId);
            if (!payment.isSuccess()) {
                throw new RuntimeException("Payment declined");
            }
        } catch (Exception err) {
            inventoryService.releaseItems(request.getItems());
            Map<String, Object> auditMeta = new HashMap<>();
            auditMeta.put("userId", userId);
            auditMeta.put("total", total);
            auditMeta.put("error", err.getMessage());
            auditService.log("CHECKOUT_PAYMENT_FAILED", auditMeta);
            return CheckoutResult.failure("Payment failed");
        }

        // Step 3: Instantiate immutable order records
        CreateOrderDTO createDTO = new CreateOrderDTO(
            userId,
            request.getItems(),
            total,
            payment.getTransactionId()
        );
        Order order = orderService.create(createDTO);

        // Step 4: Fire-and-forget notifications
        new Thread(() -> {
            try {
                notificationService.sendOrderConfirmation(user.getEmail(), order);
            } catch (Exception err) {
                System.err.println("[Async Error Fallback] Notification failed for user " + user.getId() + ": " + err.getMessage());
            }
        }).start();

        // Step 5: Master audit ledger logging entries
        Map<String, Object> auditMeta = new HashMap<>();
        auditMeta.put("orderId", order.getId());
        auditMeta.put("userId", userId);
        auditMeta.put("total", total);
        auditMeta.put("paymentId", payment.getTransactionId());
        auditService.log("ORDER_PLACED", auditMeta);

        return CheckoutResult.success(order);
    }

    public CheckoutResult cancelOrder(String orderId) {
        Order order = orderService.findById(orderId);
        if (order == null) {
            return CheckoutResult.failure("Order not found");
        }

        User user = userRepo.findById(order.getUserId());
        String email = (user != null) ? user.getEmail() : "unknown@domain.internal";

        try {
            // Refund payment settlement layer
            paymentService.refund(order.getPaymentId(), order.getTotal(), "Order cancelled");

            // Deallocate resource reservation locks
            inventoryService.releaseItems(order.getItems());

            // Mutate storage lifecycle flags
            orderService.cancel(orderId);

            // Broadcast notifications
            notificationService.sendOrderCancelled(email, order);
            notificationService.sendRefundProcessed(email, order, order.getTotal());

            // Audit trace mapping
            Map<String, Object> auditMeta = new HashMap<>();
            auditMeta.put("orderId", orderId);
            auditMeta.put("userId", order.getUserId());
            auditService.log("ORDER_CANCELLED", auditMeta);

            Order cancelledOrder = new Order(
                order.getId(),
                order.getUserId(),
                order.getItems(),
                order.getTotal(),
                order.getPaymentId(),
                "cancelled",
                order.getCreatedAt()
            );

            return CheckoutResult.success(cancelledOrder);
        } catch (Exception e) {
            return CheckoutResult.failure("Cancellation failed: " + e.getMessage());
        }
    }
}
