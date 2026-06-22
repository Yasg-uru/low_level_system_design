package com.lld.patterns.behavioral.observer;

import java.time.Instant;
import java.util.List;

/**
 * Driver client class demonstrating the Observer Pattern in Java.
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("=============================================================");
        System.out.println("=== Observer Design Pattern: Order Events Demo (Java)   ===");
        System.out.println("=============================================================\n");

        OrderEventEmitter orderEventEmitter = new OrderEventEmitter();

        NotificationService notificationService = new NotificationService();
        LoggingService loggingService = new LoggingService();
        AuditService auditService = new AuditService();
        LoyaltyPointAwarder loyaltyPointAwarder = new LoyaltyPointAwarder();

        // Registering observers
        orderEventEmitter.attach(notificationService);
        orderEventEmitter.attach(loggingService);
        orderEventEmitter.attach(auditService);
        orderEventEmitter.attach(loyaltyPointAwarder);

        Order sampleOrder = new Order("12345", List.of("item1", "item2"), 100.0);

        System.out.println("--- Triggering Notification for ORDER_PLACED ---");
        orderEventEmitter.notify(new OrderEvent(
                OrderEventType.ORDER_PLACED, 
                sampleOrder, 
                Instant.now()
        )).join(); // Join to wait for asynchronous completion and align console prints

        System.out.println("\n--- Detaching Notification Service and Triggering ORDER_SHIPPED ---");
        orderEventEmitter.detach(notificationService);

        orderEventEmitter.notify(new OrderEvent(
                OrderEventType.ORDER_SHIPPED, 
                sampleOrder, 
                Instant.now()
        )).join();
    }
}
