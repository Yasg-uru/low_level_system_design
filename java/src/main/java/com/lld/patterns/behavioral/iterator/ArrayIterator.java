package com.lld.patterns.behavioral.iterator;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * Concrete iterator that traverses an in-memory list sequentially.
 *
 * @param <T> the type of elements
 */
public class ArrayIterator<T> implements IIterator<T> {
    private final List<T> items;
    private int index = 0;

    public ArrayIterator(List<T> items) {
        this.items = items;
    }

    @Override
    public boolean hasNext() {
        return index < items.size();
    }

    @Override
    public T next() {
        if (!hasNext()) {
            throw new NoSuchElementException("No more elements in ArrayIterator");
        }
        return items.get(index++);
    }

    @Override
    public void reset() {
        this.index = 0;
    }

    @Override
    public T peek() {
        return hasNext() ? items.get(index) : null;
    }
}
