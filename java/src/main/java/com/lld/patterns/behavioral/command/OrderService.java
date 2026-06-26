package com.lld.patterns.behavioral.command;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Mock business service to manage order lifecycle.
 */
public class OrderService {
    private final Map<String, Order> database = new HashMap<>();

    public Order create(String userId, List<CartItem> items) {
        String orderId = "ord_" + UUID.randomUUID().toString().substring(0, 8);
        double total = items.size() * 100.00; // Mock base calculation
        Order newOrder = new Order(orderId, userId, items, total, OrderStatus.PENDING);
        database.put(orderId, newOrder);
        return newOrder;
    }

    public Order findById(String id) {
        return database.get(id);
    }

    public void updateStatus(String id, OrderStatus status) {
        Order order = database.get(id);
        if (order != null) {
            order.setStatus(status);
        }
    }

    public void updateTotal(String id, double total) {
        Order order = database.get(id);
        if (order != null) {
            order.setTotal(total);
        }
    }

    public void cancel(String id) {
        updateStatus(id, OrderStatus.CANCELLED);
    }
}
