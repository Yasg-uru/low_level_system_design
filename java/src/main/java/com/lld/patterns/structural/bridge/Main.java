package com.lld.patterns.structural.bridge;

/**
 * Driver client class demonstrating the Bridge Pattern in Java.
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("=============================================================");
        System.out.println("=== Bridge Design Pattern: Backend Notification Demo (Java) ===");
        System.out.println("=============================================================\n");

        System.out.println("--- Manual Combinations ---");
        Notification normalEmail = new NormalNotification(new EmailSender());
        Notification urgentSMS   = new UrgentNotification(new SmsSender());
        Notification criticalSMS = new CriticalNotification(new SmsSender(), new PushSender());

        // Block and wait (.join()) to sync output for verification purposes
        normalEmail.send("user@test.com", "Order shipped", "Your order is on its way").join();
        System.out.println();
        
        urgentSMS.send("+919876543210", "Payment failed", "Please update your card").join();
        System.out.println();
        
        criticalSMS.send("+919876543210", "Server down", "Production database unreachable").join();
        System.out.println();

        System.out.println("--- Service-Driven (Runtime) Combinations ---");
        AlertService alerts = new AlertService();
        
        alerts.alert("critical", "email", "oncall@company.com", "DB down", "Immediate action needed").join();
        System.out.println();
        
        alerts.alert("normal", "push", "device_token_xyz", "New comment", "Someone replied").join();
    }
}
