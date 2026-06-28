package com.lld.patterns.behavioral.iterator;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Unified consumption service that processes elements using iterators
 * and generates compiled reports.
 */
public class OrderReportService {

    /**
     * Generates a report synchronously using a standard IIterator.
     *
     * @param iterator the synchronous iterator of orders
     * @return the compiled ReportData
     */
    public ReportData generateReport(IIterator<Order> iterator) {
        List<ReportRow> rows = new ArrayList<>();
        while (iterator.hasNext()) {
            Order order = iterator.next();
            rows.add(new ReportRow(order.getId(), order.getTotal(), order.getCreatedAt()));
        }
        return new ReportData(rows, LocalDateTime.now());
    }

    /**
     * Generates a report asynchronously using an IAsyncIterator.
     *
     * @param iterator the asynchronous iterator of orders
     * @return a CompletableFuture resolving to the compiled ReportData
     */
    public CompletableFuture<ReportData> generateReport(IAsyncIterator<Order> iterator) {
        List<ReportRow> rows = new ArrayList<>();
        return processAsyncNext(iterator, rows)
                .thenApply(v -> new ReportData(rows, LocalDateTime.now()));
    }

    /**
     * Recursively processes the async iterator elements without blocking threads.
     */
    private CompletableFuture<Void> processAsyncNext(IAsyncIterator<Order> iterator, List<ReportRow> rows) {
        return iterator.hasNext().thenCompose(hasNext -> {
            if (!hasNext) {
                return CompletableFuture.completedFuture(null);
            }
            return iterator.next().thenCompose(order -> {
                rows.add(new ReportRow(order.getId(), order.getTotal(), order.getCreatedAt()));
                return processAsyncNext(iterator, rows);
            });
        });
    }
}
