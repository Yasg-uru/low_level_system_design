package com.lld.patterns.creational.abstractfactory;

public class PostgresFactory implements RepositoryFactory {
    @Override
    public UserRepository createUserRepository() {
        return new PostgresUserRepository();
    }

    @Override
    public OrderRepository createOrderRepository() {
        return new PostgresOrderRepository();
    }
}
