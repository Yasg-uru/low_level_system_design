package com.lld.patterns.behavioral.observer;

import java.util.concurrent.CompletableFuture;

/**
 * Concrete Observer that awards loyalty points only on ORDER_PLACED events.
 */
public class LoyaltyPointAwarder implements IOrderObserver {
    @Override
    public CompletableFuture<Void> update(OrderEvent event) {
        if (event.type() == OrderEventType.ORDER_PLACED) {
            System.out.println(String.format("[LoyaltyPointAwarder] Awarding loyalty points for order ID: %s at %s", 
                    event.order().id(), event.timestamp().toString()));
        }
        return CompletableFuture.completedFuture(null);
    }
}
