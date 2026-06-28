package com.lld.patterns.behavioral.iterator;

import java.util.concurrent.CompletableFuture;

/**
 * Generic interface for an asynchronous iterator, suitable for network
 * or database paginated stream access.
 *
 * @param <T> the type of elements returned by this iterator
 */
public interface IAsyncIterator<T> {

    /**
     * Asynchronously checks if there are more elements in the collection.
     *
     * @return a CompletableFuture resolving to true if there are more elements, false otherwise
     */
    CompletableFuture<Boolean> hasNext();

    /**
     * Asynchronously retrieves the next element in the collection and advances the cursor.
     *
     * @return a CompletableFuture resolving to the next element
     */
    CompletableFuture<T> next();

    /**
     * Resets the iterator cursor back to the beginning of the collection.
     */
    void reset();
}
