package com.lld.patterns.behavioral.observer;

import java.util.concurrent.CompletableFuture;

/**
 * Concrete Observer that handles logging of order events.
 */
public class LoggingService implements IOrderObserver {
    @Override
    public CompletableFuture<Void> update(OrderEvent event) {
        System.out.println(String.format("[LoggingService] Logging event: %s for order ID: %s at %s", 
                event.type(), event.order().id(), event.timestamp().toString()));
        return CompletableFuture.completedFuture(null);
    }
}
