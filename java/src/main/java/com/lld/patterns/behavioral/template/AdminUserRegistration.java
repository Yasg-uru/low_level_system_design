package com.lld.patterns.behavioral.template;

import java.util.concurrent.CompletableFuture;

/**
 * Concrete implementation of UserRegistrationTemplate for system administrators.
 */
public class AdminUserRegistration extends UserRegistrationTemplate {

    @Override
    protected CompletableFuture<User> createUser(User userData) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            System.out.println("[DB] Created secure system administrative record for " + userData.getUsername());
            return userData;
        });
    }

    @Override
    protected CompletableFuture<Void> sendWelcomeEmail(User user) {
        return CompletableFuture.runAsync(() -> {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            System.out.println("🚨 SECURITY ALERT: High-level administrative entry created for " + user.getEmail());
        });
    }
}
