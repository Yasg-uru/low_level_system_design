package com.lld.patterns.structural.adapter;

import java.util.HashMap;
import java.util.Map;

/**
 * Mock postgres repository.
 */
public class PostgresOrderRepository implements IOrderRepository {
    private final Map<String, Order> databaseStore = new HashMap<>();

    @Override
    public Order save(Order order) {
        String recordId = "ord_db_" + (int) (Math.random() * 9000 + 1000);
        order.setId(recordId);
        databaseStore.put(recordId, order);
        return order;
    }

    @Override
    public Order findById(String id) {
        return databaseStore.get(id);
    }

    @Override
    public void updateStatus(String id, String status) {
        Order record = databaseStore.get(id);
        if (record != null) {
            record.setStatus(status);
        }
    }
}
