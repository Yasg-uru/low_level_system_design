package com.lld.patterns.behavioral.visitor;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Order> orders = List.of(
            new PhysicalOrder(1, 1500, "Delhi"),
            new DigitalOrder(2, 800, "john@example.com")
        );

        List<OrderVisitor> visitors = List.of(
            new TaxVisitor(),
            new EmailVisitor(),
            new AuditVisitor()
        );

        System.out.println("--- Initializing Dynamic Visitor Execution Passes ---");

        for (OrderVisitor visitor : visitors) {
            System.out.println("\n▶ Running Visitor Operation: " + visitor.getClass().getSimpleName());
            for (Order order : orders) {
                order.accept(visitor);
            }
        }
    }
}
