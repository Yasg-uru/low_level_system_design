package com.lld.patterns.behavioral.visitor;

public interface Order {
    void accept(OrderVisitor visitor);
}
