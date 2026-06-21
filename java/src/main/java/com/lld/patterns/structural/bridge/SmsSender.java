package com.lld.patterns.structural.bridge;

import java.util.concurrent.CompletableFuture;

/**
 * Concrete Implementor: SMS Sender.
 * Simulates sending an SMS via Twilio or another gateway.
 */
public class SmsSender implements INotificationSender {
    @Override
    public CompletableFuture<Void> send(String to, String subject, String body, SendOptions options) {
        String fullBody = subject + ": " + body;
        String smsBody = fullBody.length() > 160 ? fullBody.substring(0, 157) + "..." : fullBody;
        
        System.out.println(String.format("[SMS] To: %s | Retries: %d | Body: \"%s\"", 
                to, options.retries(), smsBody));
        
        return CompletableFuture.runAsync(() -> {
            try {
                Thread.sleep(30); // Simulate latency
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
    }
}
