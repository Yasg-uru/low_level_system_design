package com.lld.patterns.behavioral.command;

import java.time.LocalDateTime;

/**
 * Represents an entry in the system audit logs.
 */
public class AuditLogEntry {
    private final String action;
    private final LocalDateTime timestamp;
    private final boolean success;
    private final String error;

    public AuditLogEntry(String action, LocalDateTime timestamp, boolean success, String error) {
        this.action = action;
        this.timestamp = timestamp;
        this.success = success;
        this.error = error;
    }

    public AuditLogEntry(String action, LocalDateTime timestamp, boolean success) {
        this(action, timestamp, success, null);
    }

    public String getAction() {
        return action;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getError() {
        return error;
    }
}
