package com.lld.patterns.creational.prototype;

import java.util.HashMap;
import java.util.Map;

/**
 * Service that coordinates templates from the prototype registry and sends them.
 */
public class EmailService {
    private final PrototypeRegistry<EmailTemplate> registry;
    private final IMailer mailer;

    public EmailService(PrototypeRegistry<EmailTemplate> registry, IMailer mailer) {
        this.registry = registry;
        this.mailer = mailer;
    }

    public void sendWelcome(User user) {
        EmailTemplate template = registry.getPrototype("welcomeEmail");
        if (template == null) {
            throw new IllegalStateException("Prototype baseline 'welcomeEmail' is unconfigured.");
        }

        template
            .withSubject("Welcome " + user.getName() + "!")
            .withHeader("X-Custom-Header", "WelcomeEmail")
            .bumpVersion();

        Map<String, Object> variables = new HashMap<>();
        variables.put("userName", user.getName());
        mailer.sendEmail(template, user.getEmail(), variables);
    }

    public void sendOrderConfirmation(User user, String orderId) {
        EmailTemplate template = registry.getPrototype("orderConfirmationEmail");
        if (template == null) {
            throw new IllegalStateException("Prototype baseline 'orderConfirmationEmail' is unconfigured.");
        }

        template
            .withSubject("Order #" + orderId + " Confirmed!")
            .withHeader("X-Custom-Header", "OrderConfirmationEmail")
            .bumpVersion();

        Map<String, Object> variables = new HashMap<>();
        variables.put("orderId", orderId);
        mailer.sendEmail(template, user.getEmail(), variables);
    }

    public void sendPasswordReset(User user, String resetLink) {
        EmailTemplate template = registry.getPrototype("passwordResetEmail");
        if (template == null) {
            throw new IllegalStateException("Prototype baseline 'passwordResetEmail' is unconfigured.");
        }

        template
            .withSubject("Password Reset Request for " + user.getName())
            .withHeader("X-Custom-Header", "PasswordResetEmail")
            .bumpVersion();

        Map<String, Object> variables = new HashMap<>();
        variables.put("resetLink", resetLink);
        mailer.sendEmail(template, user.getEmail(), variables);
    }
}
