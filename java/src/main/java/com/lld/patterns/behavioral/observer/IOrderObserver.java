package com.lld.patterns.behavioral.observer;

import java.util.concurrent.CompletableFuture;

/**
 * Interface representing an Observer of Order events.
 */
public interface IOrderObserver {
    CompletableFuture<Void> update(OrderEvent event);
}
