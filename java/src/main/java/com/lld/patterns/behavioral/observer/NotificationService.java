package com.lld.patterns.behavioral.observer;

import java.util.concurrent.CompletableFuture;

/**
 * Concrete Observer that simulates sending notifications on order events.
 */
public class NotificationService implements IOrderObserver {
    @Override
    public CompletableFuture<Void> update(OrderEvent event) {
        System.out.println(String.format("[NotificationService] Sending notification for event: %s at %s", 
                event.type(), event.timestamp().toString()));
        return CompletableFuture.completedFuture(null);
    }
}
