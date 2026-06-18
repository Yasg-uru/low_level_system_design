package com.lld.patterns.structural.facade;

import java.util.Date;
import java.util.Map;

public class AuditService {
    private final IAuditRepository repo;

    public AuditService(IAuditRepository repo) {
        this.repo = repo;
    }

    public void log(String action, Map<String, Object> metadata) {
        repo.save(new AuditLog(action, metadata, new Date()));
    }
}
