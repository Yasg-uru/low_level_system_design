package com.lld.patterns.structural.bridge;

import java.util.concurrent.CompletableFuture;

/**
 * Concrete Implementor: WhatsApp Sender.
 * Simulates sending a WhatsApp message.
 */
public class WhatsAppSender implements INotificationSender {
    @Override
    public CompletableFuture<Void> send(String to, String subject, String body, SendOptions options) {
        System.out.println(String.format("[WhatsApp] To: %s | Body: *%s*\n%s", 
                to, subject, body));
        
        return CompletableFuture.runAsync(() -> {
            try {
                Thread.sleep(60); // Simulate latency
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
    }
}
