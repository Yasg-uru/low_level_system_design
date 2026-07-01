package com.lld.patterns.behavioral.visitor;

public interface OrderVisitor {
    void visit(PhysicalOrder order);
    void visit(DigitalOrder order);
}
