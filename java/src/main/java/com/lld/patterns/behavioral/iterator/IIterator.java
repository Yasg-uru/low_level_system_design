package com.lld.patterns.behavioral.iterator;

/**
 * Generic interface for a standard synchronous iterator.
 *
 * @param <T> the type of elements returned by this iterator
 */
public interface IIterator<T> {
    
    /**
     * Checks if there are more elements in the collection.
     *
     * @return true if there are more elements, false otherwise
     */
    boolean hasNext();

    /**
     * Retrieves the next element in the collection and advances the cursor.
     *
     * @return the next element
     */
    T next();

    /**
     * Resets the iterator cursor back to the beginning of the collection.
     */
    void reset();

    /**
     * Retrieves the next element in the collection without advancing the cursor.
     *
     * @return the next element, or null if there are no more elements
     */
    T peek();
}
