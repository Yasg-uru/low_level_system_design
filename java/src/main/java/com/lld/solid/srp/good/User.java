package com.lld.solid.srp.good;

/**
 * User class: Only responsible for user data.
 * Single Responsibility Principle.
 */
public class User {
    private String id;
    private String email;
    private String passwordHash;

    public User(String id, String email, String passwordHash) {
        this.id = id;
        this.email = email;
        this.passwordHash = passwordHash;
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public boolean verifyPassword(String password, IPasswordHasher hasher) {
        return hasher.verify(password, passwordHash);
    }
}
