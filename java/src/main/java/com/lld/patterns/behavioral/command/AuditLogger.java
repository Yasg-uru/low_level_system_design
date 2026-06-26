package com.lld.patterns.behavioral.command;

import java.time.format.DateTimeFormatter;

/**
 * Utility logger to track executed actions and states.
 */
public class AuditLogger {
    public static void log(AuditLogEntry entry) {
        String timestampStr = entry.getTimestamp().format(DateTimeFormatter.ISO_DATE_TIME);
        String errorPart = entry.getError() != null ? " | Error: " + entry.getError() : "";
        System.out.println("[AuditLog] [" + timestampStr + "] Success: " + entry.isSuccess() + " | Action: " + entry.getAction() + errorPart);
    }
}
