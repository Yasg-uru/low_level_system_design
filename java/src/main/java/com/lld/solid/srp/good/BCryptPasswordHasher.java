package com.lld.solid.srp.good;

/**
 * Concrete implementation: Password hashing
 */
public class BCryptPasswordHasher implements IPasswordHasher {

    @Override
    public String hash(String password) {
        return "hashed_" + password;  // Simplified for demo
    }

    @Override
    public boolean verify(String password, String hash) {
        return this.hash(password).equals(hash);
    }
}
