package com.lld.solid.srp.good;

/**
 * Responsibility: Hash passwords
 */
public interface IPasswordHasher {
    String hash(String password);
    boolean verify(String password, String hash);
}
