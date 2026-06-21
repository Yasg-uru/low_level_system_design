package com.lld.patterns.structural.bridge;

import java.util.concurrent.CompletableFuture;

/**
 * Refined Abstraction: Critical Notification.
 * Dispatches to a primary sender and automatically triggers escalation alert to an on-call contact.
 */
public class CriticalNotification extends Notification {
    private final INotificationSender escalationSender;
    private static final String ON_CALL_PHONE = "+15550199";

    public CriticalNotification(INotificationSender sender, INotificationSender escalationSender) {
        super(sender);
        this.escalationSender = escalationSender;
    }

    @Override
    public CompletableFuture<Void> send(String to, String subject, String body) {
        CompletableFuture<Void> primarySend = this.sender.send(to, "🔴 CRITICAL: " + subject, body, new SendOptions(10, 30000));
        
        CompletableFuture<Void> escalationSend = this.escalationSender.send(
                ON_CALL_PHONE, 
                "CRITICAL ESCALATION: " + subject, 
                body, 
                new SendOptions(10, 30000)
        );

        return CompletableFuture.allOf(primarySend, escalationSend);
    }
}
