package com.lld.patterns.structural.bridge;

import java.util.concurrent.CompletableFuture;

/**
 * Refined Abstraction: Normal Notification.
 * Configured with standard retry and timeout rules.
 */
public class NormalNotification extends Notification {
    public NormalNotification(INotificationSender sender) {
        super(sender);
    }

    @Override
    public CompletableFuture<Void> send(String to, String subject, String body) {
        return this.sender.send(to, subject, body, new SendOptions(1, 5000));
    }
}
