package com.lld.patterns.creational.abstractfactory;

public interface RepositoryFactory {
    UserRepository createUserRepository();
    OrderRepository createOrderRepository();
}
