package com.lld.patterns.structural.bridge;

import java.util.concurrent.CompletableFuture;

/**
 * The Implementor Interface in the Bridge Pattern.
 * Defines the operations that all concrete delivery channels must implement.
 */
public interface INotificationSender {
    CompletableFuture<Void> send(String to, String subject, String body, SendOptions options);
}
