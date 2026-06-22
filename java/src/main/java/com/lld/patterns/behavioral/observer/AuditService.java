package com.lld.patterns.behavioral.observer;

import java.util.concurrent.CompletableFuture;

/**
 * Concrete Observer that performs auditing on order events.
 */
public class AuditService implements IOrderObserver {
    @Override
    public CompletableFuture<Void> update(OrderEvent event) {
        System.out.println(String.format("[AuditService] Auditing event: %s for order ID: %s at %s", 
                event.type(), event.order().id(), event.timestamp().toString()));
        return CompletableFuture.completedFuture(null);
    }
}
