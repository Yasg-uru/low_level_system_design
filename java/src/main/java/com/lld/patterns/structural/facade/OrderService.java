package com.lld.patterns.structural.facade;

import java.util.Date;
import java.util.UUID;

public class OrderService {
    private final IOrderRepository repo;

    public OrderService(IOrderRepository repo) {
        this.repo = repo;
    }

    public Order create(CreateOrderDTO data) {
        String id = "ord_" + UUID.randomUUID().toString().substring(0, 8);
        Order order = new Order(
            id,
            data.getUserId(),
            data.getItems(),
            data.getTotal(),
            data.getPaymentId(),
            "confirmed",
            new Date()
        );
        return repo.save(order);
    }

    public void cancel(String orderId) {
        repo.updateStatus(orderId, "cancelled");
    }

    public Order findById(String orderId) {
        return repo.findById(orderId);
    }
}
