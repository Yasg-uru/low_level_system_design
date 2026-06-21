package com.lld.patterns.structural.bridge;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * Runtime Usage Service demonstrating decoupled channel and notification logic.
 */
public class AlertService {
    private final Map<String, INotificationSender> channelMap = new HashMap<>();

    public AlertService() {
        channelMap.put("email", new EmailSender());
        channelMap.put("sms", new SmsSender());
        channelMap.put("push", new PushSender());
        channelMap.put("whatsapp", new WhatsAppSender());
    }

    public CompletableFuture<Void> alert(
            String urgency,
            String channel,
            String to,
            String subject,
            String body) {
        
        INotificationSender sender = channelMap.get(channel.toLowerCase());
        if (sender == null) {
            throw new IllegalArgumentException("Unsupported channel: " + channel);
        }

        Notification notification;
        switch (urgency.toLowerCase()) {
            case "critical" -> notification = new CriticalNotification(sender, channelMap.get("sms"));
            case "urgent"   -> notification = new UrgentNotification(sender);
            default         -> notification = new NormalNotification(sender);
        }

        return notification.send(to, subject, body);
    }
}
