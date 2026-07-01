package com.lld.patterns.behavioral.visitor;

public class EmailVisitor implements OrderVisitor {
    @Override
    public void visit(PhysicalOrder order) {
        System.out.println("   [Email Service] Shipping confirmation dispatched to address: " + order.getShippingAddress());
    }

    @Override
    public void visit(DigitalOrder order) {
        System.out.println("   [Email Service] Secure item download links sent to: " + order.getEmail());
    }
}
