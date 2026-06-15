package com.lld.patterns.creational.abstractfactory;

public class UserService {
    private final RepositoryFactory factory;

    public UserService(RepositoryFactory factory) {
        this.factory = factory;
    }

    public void getUserProfile(int userId) {
        UserRepository userRepo = factory.createUserRepository();
        OrderRepository orderRepo = factory.createOrderRepository();

        userRepo.getUser(userId);
        orderRepo.getOrder(1001);
    }
}
