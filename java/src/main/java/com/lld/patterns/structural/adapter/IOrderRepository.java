package com.lld.patterns.structural.adapter;

/**
 * Interface representing target database capabilities.
 */
public interface IOrderRepository {
    Order save(Order order);
    Order findById(String id);
    void updateStatus(String id, String status);
}
