package com.lld.patterns.creational.abstractfactory;

public class MongoFactory implements RepositoryFactory {
    @Override
    public UserRepository createUserRepository() {
        return new MongoUserRepository();
    }

    @Override
    public OrderRepository createOrderRepository() {
        return new MongoOrderRepository();
    }
}
