package com.lld.patterns.behavioral.iterator;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

/**
 * Driver client demonstrating the Iterator Pattern in Java.
 */
public class Main {
    public static void main(String[] args) throws Exception {
        System.out.println("=============================================================");
        System.out.println("=== Iterator Design Pattern: Order Reports (Java)         ===");
        System.out.println("=============================================================\n");

        OrderRepository orderRepo = new OrderRepository();
        OrderReportService reportService = new OrderReportService();

        // 1. Array Execution Data Source
        List<Order> recentOrders = Arrays.asList(
            new Order("local_1", 150.00, "completed", LocalDateTime.now()),
            new Order("local_2", 240.00, "cancelled", LocalDateTime.now())
        );

        System.out.println("--- Report From Local Array Data Source ---");
        IIterator<Order> arrayIterator = new ArrayIterator<>(recentOrders);
        ReportData inMemoryReport = reportService.generateReport(arrayIterator);
        System.out.println("Generated lines: " + inMemoryReport.getRows().size());
        for (ReportRow row : inMemoryReport.getRows()) {
            System.out.println("  - Row ID: " + row.getId() + ", Total: $" + row.getTotal());
        }

        // 2. Paginated Remote Repository Stream Source
        System.out.println("\n--- Report From Remote Paginated DB Source ---");
        IAsyncIterator<Order> paginatedIterator = new PaginatedOrderIterator(orderRepo, "user_123", 5);
        // Using .get() here to block Main thread just for demo output completion
        ReportData dbReport = reportService.generateReport(paginatedIterator).get();
        System.out.println("Generated database lines: " + dbReport.getRows().size());
        System.out.println("First few entries:");
        dbReport.getRows().stream().limit(3).forEach(row -> 
            System.out.println("  - Row ID: " + row.getId() + ", Total: $" + row.getTotal())
        );

        // 3. Filtered Sync Iterator Operations pipeline
        System.out.println("\n--- Filtered Report (Only Cancelled Items) ---");
        IIterator<Order> filteredIterator = new FilteredIterator<>(
            new ArrayIterator<>(recentOrders),
            order -> "cancelled".equalsIgnoreCase(order.getStatus())
        );
        ReportData cancelledReport = reportService.generateReport(filteredIterator);
        System.out.println("Cancelled lines extracted: " + cancelledReport.getRows().size());
        for (ReportRow row : cancelledReport.getRows()) {
            System.out.println("  - Row ID: " + row.getId() + ", Total: $" + row.getTotal());
        }
    }
}
