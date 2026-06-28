package com.lld.patterns.behavioral.template;

import java.util.concurrent.CompletableFuture;

/**
 * The Abstract Template Class defining the skeleton of the user registration workflow.
 */
public abstract class UserRegistrationTemplate {

    /**
     * The Template Method: defines the invariant sequential skeleton of the workflow.
     * Enforced as 'final' to prevent subclasses from modifying the structure.
     *
     * @param userData the user registration data
     * @return a CompletableFuture resolving to the registered User
     */
    public final CompletableFuture<User> registerUser(User userData) {
        try {
            validateUserData(userData);
            return createUser(userData)
                    .thenCompose(user -> sendWelcomeEmail(user)
                            .thenApply(v -> {
                                logRegistration(user);
                                return user;
                            }));
        } catch (Exception e) {
            CompletableFuture<User> failedFuture = new CompletableFuture<>();
            failedFuture.completeExceptionally(e);
            return failedFuture;
        }
    }

    /**
     * Common validation step shared by all subclasses.
     *
     * @param userData the user registration data
     */
    protected void validateUserData(User userData) {
        if (userData.getUsername() == null || userData.getUsername().trim().isEmpty() ||
            userData.getEmail() == null || userData.getEmail().trim().isEmpty() ||
            userData.getPassword() == null || userData.getPassword().trim().isEmpty()) {
            throw new IllegalArgumentException("All fields are required");
        }
        if (!isValidEmail(userData.getEmail())) {
            throw new IllegalArgumentException("Invalid email format");
        }
        if (!isStrongPassword(userData.getPassword())) {
            throw new IllegalArgumentException("Password must be at least 8 characters long and contain a mix of letters and numbers");
        }
    }

    /**
     * Primitive operation to be customized by concrete subclasses for user creation.
     */
    protected abstract CompletableFuture<User> createUser(User userData);

    /**
     * Primitive operation to be customized by concrete subclasses for sending welcome emails.
     */
    protected abstract CompletableFuture<Void> sendWelcomeEmail(User user);

    /**
     * Hook method offering a default shared implementation. Subclasses can optionally override this.
     */
    protected void logRegistration(User user) {
        System.out.println("[Log] User registered: " + user.getUsername() + " (" + user.getEmail() + ")");
    }

    private boolean isValidEmail(String email) {
        return email != null && email.matches("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$");
    }

    private boolean isStrongPassword(String password) {
        return password != null && password.length() >= 8 &&
               password.matches(".*[A-Za-z].*") && password.matches(".*[0-9].*");
    }
}
