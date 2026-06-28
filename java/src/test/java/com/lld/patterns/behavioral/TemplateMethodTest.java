package com.lld.patterns.behavioral;

import com.lld.patterns.behavioral.template.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Template Method Design Pattern Tests")
class TemplateMethodTest {

    @Test
    @DisplayName("Customer registration should run successfully and return customer details")
    void testCustomerRegistrationSuccess() throws Exception {
        UserRegistrationTemplate registration = new CustomerUserRegistration();
        User input = new User("alice", "alice@test.com", "AlicePass123");

        User result = registration.registerUser(input).get();

        assertNotNull(result);
        assertEquals("alice", result.getUsername());
        assertEquals("alice@test.com", result.getEmail());
    }

    @Test
    @DisplayName("Admin registration should run successfully and return admin details")
    void testAdminRegistrationSuccess() throws Exception {
        UserRegistrationTemplate registration = new AdminUserRegistration();
        User input = new User("admin_bob", "bob@admin.com", "SecureBob99");

        User result = registration.registerUser(input).get();

        assertNotNull(result);
        assertEquals("admin_bob", result.getUsername());
        assertEquals("bob@admin.com", result.getEmail());
    }

    @Test
    @DisplayName("Registration should fail with IllegalArgumentException when username is empty")
    void testRegistrationEmptyUsername() {
        UserRegistrationTemplate registration = new CustomerUserRegistration();
        User input = new User("", "alice@test.com", "AlicePass123");

        ExecutionException exception = assertThrows(ExecutionException.class, () -> registration.registerUser(input).get());
        assertTrue(exception.getCause() instanceof IllegalArgumentException);
        assertEquals("All fields are required", exception.getCause().getMessage());
    }

    @Test
    @DisplayName("Registration should fail with IllegalArgumentException when email format is invalid")
    void testRegistrationInvalidEmail() {
        UserRegistrationTemplate registration = new CustomerUserRegistration();
        User input = new User("alice", "invalid-email", "AlicePass123");

        ExecutionException exception = assertThrows(ExecutionException.class, () -> registration.registerUser(input).get());
        assertTrue(exception.getCause() instanceof IllegalArgumentException);
        assertEquals("Invalid email format", exception.getCause().getMessage());
    }

    @Test
    @DisplayName("Registration should fail with IllegalArgumentException when password is weak")
    void testRegistrationWeakPassword() {
        UserRegistrationTemplate registration = new CustomerUserRegistration();
        User input = new User("alice", "alice@test.com", "weak");

        ExecutionException exception = assertThrows(ExecutionException.class, () -> registration.registerUser(input).get());
        assertTrue(exception.getCause() instanceof IllegalArgumentException);
        assertTrue(exception.getCause().getMessage().contains("Password must be at least 8 characters"));
    }
}
