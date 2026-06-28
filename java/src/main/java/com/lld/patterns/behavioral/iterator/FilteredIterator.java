package com.lld.patterns.behavioral.iterator;

import java.util.NoSuchElementException;
import java.util.function.Predicate;

/**
 * Concrete iterator that wraps another synchronous iterator and filters elements
 * based on a given Predicate on-the-fly.
 *
 * @param <T> the type of elements
 */
public class FilteredIterator<T> implements IIterator<T> {
    private final IIterator<T> inner;
    private final Predicate<T> predicate;
    private T nextItem = null;
    private boolean fetched = false;

    public FilteredIterator(IIterator<T> inner, Predicate<T> predicate) {
        this.inner = inner;
        this.predicate = predicate;
    }

    @Override
    public boolean hasNext() {
        if (fetched) {
            return nextItem != null;
        }
        fetchNext();
        return nextItem != null;
    }

    @Override
    public T next() {
        if (!fetched) {
            fetchNext();
        }
        if (nextItem == null) {
            throw new NoSuchElementException("No more elements matching predicate in FilteredIterator");
        }
        T item = nextItem;
        nextItem = null;
        fetched = false;
        return item;
    }

    @Override
    public void reset() {
        inner.reset();
        nextItem = null;
        fetched = false;
    }

    @Override
    public T peek() {
        hasNext();
        return nextItem;
    }

    private void fetchNext() {
        fetched = true;
        nextItem = null;
        while (inner.hasNext()) {
            T candidate = inner.next();
            if (predicate.test(candidate)) {
                nextItem = candidate;
                return;
            }
        }
    }
}
