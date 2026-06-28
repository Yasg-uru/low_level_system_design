package com.lld.patterns.behavioral.template;

/**
 * Driver client demonstrating the Template Method Pattern in Java.
 */
public class Main {
    public static void main(String[] args) throws Exception {
        System.out.println("=============================================================");
        System.out.println("=== Template Method Design Pattern: User Registration (Java)===");
        System.out.println("=============================================================\n");

        System.out.println("--- Executing Customer Workflow Pipeline ---");
        UserRegistrationTemplate customerRegistration = new CustomerUserRegistration();
        customerRegistration.registerUser(new User(
            "john_doe",
            "john.doe@example.com",
            "SecurePass123"
        )).get(); // block to wait for async completion for demo output

        System.out.println("\n--- Executing Admin Workflow Pipeline ---");
        UserRegistrationTemplate adminRegistration = new AdminUserRegistration();
        adminRegistration.registerUser(new User(
            "root_admin",
            "admin@company.com",
            "AdminPass123"
        )).get(); // block to wait for async completion for demo output
    }
}
