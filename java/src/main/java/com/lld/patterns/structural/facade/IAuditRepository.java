package com.lld.patterns.structural.facade;

public interface IAuditRepository {
    void save(AuditLog log);
}
