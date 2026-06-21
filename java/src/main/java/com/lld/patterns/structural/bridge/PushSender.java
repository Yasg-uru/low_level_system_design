package com.lld.patterns.structural.bridge;

import java.util.concurrent.CompletableFuture;

/**
 * Concrete Implementor: Push Sender.
 * Simulates sending a mobile push notification via FCM.
 */
public class PushSender implements INotificationSender {
    @Override
    public CompletableFuture<Void> send(String to, String subject, String body, SendOptions options) {
        System.out.println(String.format("[Push] Device Token: %s | Retries: %d | Title: %s", 
                to, options.retries(), subject));
        
        return CompletableFuture.runAsync(() -> {
            try {
                Thread.sleep(40); // Simulate latency
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
    }
}
