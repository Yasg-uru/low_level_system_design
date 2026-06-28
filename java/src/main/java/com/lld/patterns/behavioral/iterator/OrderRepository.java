package com.lld.patterns.behavioral.iterator;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Mock repository simulating a database or remote API client that retrieves
 * order data using page-based pagination.
 */
public class OrderRepository {
    private final List<Order> mockData = new ArrayList<>();

    public OrderRepository() {
        // Initialize 15 mock orders, similar to the TypeScript implementation
        for (int i = 0; i < 15; i++) {
            String id = "ord_" + (i + 1);
            double total = (i + 1) * 75.00;
            String status = (i % 3 == 0) ? "cancelled" : "completed";
            mockData.add(new Order(id, total, status, LocalDateTime.now()));
        }
    }

    /**
     * Simulates fetching a page of orders asynchronously (e.g. from an database or API).
     *
     * @param userId the user who owns the orders
     * @param pageIndex 0-based page index
     * @param pageSize size of the page
     * @return a CompletableFuture containing the list of orders for the requested page
     */
    public CompletableFuture<List<Order>> fetchPage(String userId, int pageIndex, int pageSize) {
        return CompletableFuture.supplyAsync(() -> {
            // Simulate brief network / database processing latency
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            int start = pageIndex * pageSize;
            if (start >= mockData.size()) {
                return new ArrayList<>();
            }
            int end = Math.min(start + pageSize, mockData.size());
            return new ArrayList<>(mockData.subList(start, end));
        });
    }
}
