package com.lld.patterns.behavioral.state;

/**
 * Driver client demonstrating the State Design Pattern in Java.
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("=============================================================");
        System.out.println("=== State Design Pattern: Order Lifecycle State Machine (Java) ===");
        System.out.println("=============================================================\n");

        System.out.println("--- 🛒 Scenario A: Standard Operational Path ---");
        Order myOrder = new Order();
        System.out.println("Current: " + myOrder.getStatus() + " | Options: " + myOrder.getAllowedActions());

        myOrder.confirm();
        System.out.println("Current: " + myOrder.getStatus() + " | Options: " + myOrder.getAllowedActions());

        myOrder.ship();
        System.out.println("Current: " + myOrder.getStatus() + " | Options: " + myOrder.getAllowedActions());

        myOrder.deliver();
        System.out.println("Current: " + myOrder.getStatus() + " | Options: " + myOrder.getAllowedActions());

        myOrder.requestReturn();
        System.out.println("Current: " + myOrder.getStatus() + " | Options: " + myOrder.getAllowedActions());

        System.out.println("\n--- 🛒 Scenario B: Exception / Validation Guard Interception ---");
        Order failedOrder = new Order();
        try {
            failedOrder.ship(); // Invalid action call path
        } catch (IllegalStateException e) {
            System.err.println("Caught expected rule rejection: " + e.getMessage());
        }
    }
}
