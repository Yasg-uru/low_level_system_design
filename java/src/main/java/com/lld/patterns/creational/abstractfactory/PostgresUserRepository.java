package com.lld.patterns.creational.abstractfactory;

public class PostgresUserRepository implements UserRepository {
    @Override
    public void getUser(int id) {
        System.out.println("[PostgreSQL] Fetching user with ID: " + id);
    }
}
