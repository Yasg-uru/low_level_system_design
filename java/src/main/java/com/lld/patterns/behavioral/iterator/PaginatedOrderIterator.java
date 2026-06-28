package com.lld.patterns.behavioral.iterator;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.CompletableFuture;

/**
 * An asynchronous iterator that fetches and consumes database pages
 * page-by-page from an OrderRepository.
 */
public class PaginatedOrderIterator implements IAsyncIterator<Order> {
    private final OrderRepository repo;
    private final String userId;
    private final int pageSize;

    private List<Order> currentPage = new ArrayList<>();
    private int pageIndex = 0;
    private int itemIndex = 0;
    private boolean exhausted = false;

    public PaginatedOrderIterator(OrderRepository repo, String userId, int pageSize) {
        this.repo = repo;
        this.userId = userId;
        this.pageSize = pageSize;
    }

    @Override
    public CompletableFuture<Boolean> hasNext() {
        if (itemIndex < currentPage.size()) {
            return CompletableFuture.completedFuture(true);
        }
        if (exhausted) {
            return CompletableFuture.completedFuture(false);
        }
        return fetchNextPage().thenApply(v -> !currentPage.isEmpty());
    }

    @Override
    public CompletableFuture<Order> next() {
        return hasNext().thenApply(has -> {
            if (!has) {
                throw new NoSuchElementException("No more elements in PaginatedOrderIterator");
            }
            return currentPage.get(itemIndex++);
        });
    }

    @Override
    public void reset() {
        this.currentPage = new ArrayList<>();
        this.pageIndex = 0;
        this.itemIndex = 0;
        this.exhausted = false;
    }

    private CompletableFuture<Void> fetchNextPage() {
        return repo.fetchPage(userId, pageIndex, pageSize)
                .thenAccept(data -> {
                    if (data == null || data.isEmpty()) {
                        exhausted = true;
                        currentPage = new ArrayList<>();
                    } else {
                        currentPage = data;
                        pageIndex++;
                        itemIndex = 0;
                    }
                });
    }
}
