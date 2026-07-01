package com.lld.patterns.behavioral.visitor;

public class AuditVisitor implements OrderVisitor {
    @Override
    public void visit(PhysicalOrder order) {
        System.out.println("   [Audit Log] System scanned Physical Order record ID: " + order.getId());
    }

    @Override
    public void visit(DigitalOrder order) {
        System.out.println("   [Audit Log] System scanned Digital Order record ID: " + order.getId());
    }
}
