package com.lld.patterns.behavioral;

import com.lld.patterns.behavioral.iterator.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Iterator Design Pattern Tests")
class IteratorTest {

    private List<Order> mockOrders;

    @BeforeEach
    void setUp() {
        mockOrders = Arrays.asList(
            new Order("ord_1", 100.0, "completed", LocalDateTime.now()),
            new Order("ord_2", 200.0, "cancelled", LocalDateTime.now()),
            new Order("ord_3", 300.0, "completed", LocalDateTime.now())
        );
    }

    @Test
    @DisplayName("ArrayIterator should traverse items sequentially")
    void testArrayIteratorTraversal() {
        IIterator<Order> iterator = new ArrayIterator<>(mockOrders);

        assertTrue(iterator.hasNext());
        assertEquals("ord_1", iterator.peek().getId());
        assertEquals("ord_1", iterator.next().getId());

        assertTrue(iterator.hasNext());
        assertEquals("ord_2", iterator.peek().getId());
        assertEquals("ord_2", iterator.next().getId());

        assertTrue(iterator.hasNext());
        assertEquals("ord_3", iterator.next().getId());

        assertFalse(iterator.hasNext());
        assertNull(iterator.peek());
        assertThrows(NoSuchElementException.class, iterator::next);
    }

    @Test
    @DisplayName("ArrayIterator should support reset")
    void testArrayIteratorReset() {
        IIterator<Order> iterator = new ArrayIterator<>(mockOrders);

        assertEquals("ord_1", iterator.next().getId());
        iterator.reset();
        
        assertTrue(iterator.hasNext());
        assertEquals("ord_1", iterator.next().getId());
    }

    @Test
    @DisplayName("FilteredIterator should dynamically filter elements based on predicate")
    void testFilteredIterator() {
        IIterator<Order> arrayIterator = new ArrayIterator<>(mockOrders);
        IIterator<Order> iterator = new FilteredIterator<>(arrayIterator, order -> "completed".equals(order.getStatus()));

        assertTrue(iterator.hasNext());
        assertEquals("ord_1", iterator.peek().getId());
        assertEquals("ord_1", iterator.next().getId());

        assertTrue(iterator.hasNext());
        assertEquals("ord_3", iterator.next().getId());

        assertFalse(iterator.hasNext());
        assertThrows(NoSuchElementException.class, iterator::next);
    }

    @Test
    @DisplayName("FilteredIterator should support reset")
    void testFilteredIteratorReset() {
        IIterator<Order> arrayIterator = new ArrayIterator<>(mockOrders);
        IIterator<Order> iterator = new FilteredIterator<>(arrayIterator, order -> "completed".equals(order.getStatus()));

        assertEquals("ord_1", iterator.next().getId());
        iterator.reset();

        assertTrue(iterator.hasNext());
        assertEquals("ord_1", iterator.next().getId());
    }

    @Test
    @DisplayName("PaginatedOrderIterator should retrieve all 15 elements across multiple pages asynchronously")
    void testPaginatedOrderIterator() throws ExecutionException, InterruptedException {
        OrderRepository repo = new OrderRepository();
        IAsyncIterator<Order> iterator = new PaginatedOrderIterator(repo, "user_abc", 5);

        int count = 0;
        while (iterator.hasNext().get()) {
            Order order = iterator.next().get();
            assertNotNull(order);
            count++;
            assertEquals("ord_" + count, order.getId());
        }

        assertEquals(15, count);
        assertFalse(iterator.hasNext().get());
    }

    @Test
    @DisplayName("OrderReportService should generate report correctly from sync iterator")
    void testOrderReportServiceSync() {
        IIterator<Order> iterator = new ArrayIterator<>(mockOrders);
        OrderReportService service = new OrderReportService();
        ReportData report = service.generateReport(iterator);

        assertNotNull(report);
        assertEquals(3, report.getRows().size());
        assertEquals("ord_1", report.getRows().get(0).getId());
        assertEquals(100.0, report.getRows().get(0).getTotal());
    }

    @Test
    @DisplayName("OrderReportService should generate report correctly from async iterator")
    void testOrderReportServiceAsync() throws ExecutionException, InterruptedException {
        OrderRepository repo = new OrderRepository();
        IAsyncIterator<Order> iterator = new PaginatedOrderIterator(repo, "user_abc", 5);
        OrderReportService service = new OrderReportService();

        ReportData report = service.generateReport(iterator).get();

        assertNotNull(report);
        assertEquals(15, report.getRows().size());
        assertEquals("ord_1", report.getRows().get(0).getId());
        assertEquals(75.0, report.getRows().get(0).getTotal());
    }
}
