package com.lld.patterns.creational.abstractfactory;

public class PostgresOrderRepository implements OrderRepository {
    @Override
    public void getOrder(int id) {
        System.out.println("[PostgreSQL] Fetching order with ID: " + id);
    }
}
