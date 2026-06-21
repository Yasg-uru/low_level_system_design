package com.lld.patterns.structural.bridge;

import java.time.LocalDateTime;

/**
 * Simulates a third-party or internal incident tracking system.
 */
public class IncidentTracker {
    public static void record(String to, String subject) {
        System.out.println(String.format("[Tracker] Logged incident for %s regarding '%s' at %s", to, subject, LocalDateTime.now()));
    }
}
