package com.lld.patterns.creational.prototype;

import java.util.HashMap;
import java.util.Map;

/**
 * Main class to demonstrate the Prototype pattern with a Registry in Java.
 */
public class Main {
    public static void main(String[] args) {
        PrototypeRegistry<EmailTemplate> emailRegistry = new PrototypeRegistry<>();

        // Seeding the default core system blueprints
        Map<String, Object> welcomeMetadata = new HashMap<>();
        welcomeMetadata.put("category", "welcome");
        welcomeMetadata.put("priority", "high");
        emailRegistry.registerPrototype(
            "welcomeEmail",
            new EmailTemplate(
                "Welcome to our service!",
                "<h1>Welcome!</h1><p>Thank you for joining our service.</p>",
                "noreply@ourservice.com",
                welcomeMetadata
            )
        );

        Map<String, Object> orderMetadata = new HashMap<>();
        orderMetadata.put("category", "confirmation");
        orderMetadata.put("priority", "high");
        emailRegistry.registerPrototype(
            "orderConfirmationEmail",
            new EmailTemplate(
                "Your order has been confirmed!",
                "<h1>Order Confirmed!</h1><p>Your order has been successfully placed.</p>",
                "noreply@ourservice.com",
                orderMetadata
            )
        );

        Map<String, Object> resetMetadata = new HashMap<>();
        resetMetadata.put("category", "reset");
        resetMetadata.put("priority", "high");
        emailRegistry.registerPrototype(
            "passwordResetEmail",
            new EmailTemplate(
                "Password Reset Request",
                "<h1>Password Reset</h1><p>Click the link below to change credentials.</p>",
                "noreply@ourservice.com",
                resetMetadata
            )
        );

        // Runtime Pipeline Demonstration
        IMailer mailer = new SMTPMailer();
        EmailService emailService = new EmailService(emailRegistry, mailer);

        User mockUser = new User("Yash Choudhary", "yash.choudhary@example.com");

        System.out.println("=== Execution Engine Online: Processing Pipeline Triggers ===\n");

        emailService.sendWelcome(mockUser);
        emailService.sendOrderConfirmation(mockUser, "TXN_77491");
        emailService.sendPasswordReset(mockUser, "https://ourservice.com/reset-password");
    }
}
