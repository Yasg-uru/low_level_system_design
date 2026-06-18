package com.lld.patterns.structural.facade;

public interface IUserRepository {
    User findById(String id);
}
