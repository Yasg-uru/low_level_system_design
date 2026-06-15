package com.lld.patterns.creational.abstractfactory;

public class MongoUserRepository implements UserRepository {
    @Override
    public void getUser(int id) {
        System.out.println("[MongoDB] Fetching user with ID: " + id);
    }
}
