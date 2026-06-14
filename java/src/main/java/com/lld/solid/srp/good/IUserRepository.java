package com.lld.solid.srp.good;

public interface IUserRepository {
    void add(User user);
    User get(String id);
    void delete(String id);
}
