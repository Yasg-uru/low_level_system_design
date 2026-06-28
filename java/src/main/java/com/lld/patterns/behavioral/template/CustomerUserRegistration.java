package com.lld.patterns.behavioral.template;

import java.util.concurrent.CompletableFuture;

/**
 * Concrete implementation of UserRegistrationTemplate for standard customers.
 */
public class CustomerUserRegistration extends UserRegistrationTemplate {

    @Override
    protected CompletableFuture<User> createUser(User userData) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            System.out.println("[DB] Saved customer row for " + userData.getUsername());
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
            System.out.println("[Mail] Automated customer onboarding welcome sent to " + user.getEmail());
        });
    }
}
