package com.lld.patterns.behavioral.observer;

import java.util.concurrent.CompletableFuture;

/**
 * Interface representing the Subject (event emitter) of Order events.
 */
public interface IOrderEventEmitter {
    void attach(IOrderObserver observer);
    void detach(IOrderObserver observer);
    CompletableFuture<Void> notify(OrderEvent event);
}
