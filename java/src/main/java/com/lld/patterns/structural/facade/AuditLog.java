package com.lld.patterns.structural.facade;

import java.util.Date;
import java.util.Map;

public class AuditLog {
    private final String action;
    private final Map<String, Object> metadata;
    private final Date timestamp;

    public AuditLog(String action, Map<String, Object> metadata, Date timestamp) {
        this.action = action;
        this.metadata = metadata;
        this.timestamp = timestamp;
    }

    public String getAction() { return action; }
    public Map<String, Object> getMetadata() { return metadata; }
    public Date getTimestamp() { return timestamp; }
}
