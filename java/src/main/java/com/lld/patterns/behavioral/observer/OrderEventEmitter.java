package com.lld.patterns.behavioral.observer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Concrete Subject / Emitter that registers observers and forwards events to them.
 */
public class OrderEventEmitter implements IOrderEventEmitter {
    private final List<IOrderObserver> observers = new ArrayList<>();

    @Override
    public synchronized void attach(IOrderObserver observer) {
        if (!observers.contains(observer)) {
            observers.add(observer);
        }
    }

    @Override
    public synchronized void detach(IOrderObserver observer) {
        observers.remove(observer);
    }

    @Override
    public synchronized CompletableFuture<Void> notify(OrderEvent event) {
        CompletableFuture<Void> chain = CompletableFuture.completedFuture(null);
        
        // Copy the list to avoid ConcurrentModificationException if observers change during iteration
        List<IOrderObserver> currentObservers = new ArrayList<>(this.observers);
        
        // Run observers sequentially matching the TS promise-chaining behavior
        for (IOrderObserver observer : currentObservers) {
            chain = chain.thenCompose(v -> observer.update(event));
        }
        
        return chain;
    }
}
