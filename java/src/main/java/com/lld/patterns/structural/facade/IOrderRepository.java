package com.lld.patterns.structural.facade;

public interface IOrderRepository {
    Order save(Order order);
    Order findById(String id);
    void updateStatus(String id, String status);
}
