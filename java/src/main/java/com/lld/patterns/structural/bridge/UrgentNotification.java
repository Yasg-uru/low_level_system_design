package com.lld.patterns.structural.bridge;

import java.util.concurrent.CompletableFuture;

/**
 * Refined Abstraction: Urgent Notification.
 * Configured with higher retries and logs an incident automatically.
 */
public class UrgentNotification extends Notification {
    public UrgentNotification(INotificationSender sender) {
        super(sender);
    }

    @Override
    public CompletableFuture<Void> send(String to, String subject, String body) {
        return this.sender.send(to, "🚨 URGENT: " + subject, body, new SendOptions(5, 15000))
                .thenAccept(v -> IncidentTracker.record(to, subject));
    }
}
