package com.lld.patterns.structural.bridge;

import java.util.concurrent.CompletableFuture;

/**
 * Concrete Implementor: Email Sender.
 * Simulates sending an email via an external provider.
 */
public class EmailSender implements INotificationSender {
    @Override
    public CompletableFuture<Void> send(String to, String subject, String body, SendOptions options) {
        System.out.println(String.format("[Email] To: %s | Subject: %s | Retries: %d | Timeout: %dms", 
                to, subject, options.retries(), options.timeoutMs()));
        
        return CompletableFuture.runAsync(() -> {
            try {
                Thread.sleep(50); // Simulate latency
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
    }
}
