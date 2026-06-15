package com.lld.patterns.creational.abstractfactory;

public class MongoOrderRepository implements OrderRepository {
    @Override
    public void getOrder(int id) {
        System.out.println("[MongoDB] Fetching order with ID: " + id);
    }
}
