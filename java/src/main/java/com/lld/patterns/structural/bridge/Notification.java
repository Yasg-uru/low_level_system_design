package com.lld.patterns.structural.bridge;

import java.util.concurrent.CompletableFuture;

/**
 * The Abstraction class in the Bridge Pattern.
 * Holds a reference to the implementation interface (Bridge).
 */
public abstract class Notification {
    protected INotificationSender sender;

    protected Notification(INotificationSender sender) {
        this.sender = sender;
    }

    public abstract CompletableFuture<Void> send(String to, String subject, String body);
}
