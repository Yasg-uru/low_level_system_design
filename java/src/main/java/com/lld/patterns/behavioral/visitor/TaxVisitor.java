package com.lld.patterns.behavioral.visitor;

public class TaxVisitor implements OrderVisitor {
    @Override
    public void visit(PhysicalOrder order) {
        double tax = order.getAmount() * 0.18;
        System.out.printf("   [Tax Calculation] Physical Order #%d Tax (18%%) = ₹%.2f%n", order.getId(), tax);
    }

    @Override
    public void visit(DigitalOrder order) {
        double tax = order.getAmount() * 0.12;
        System.out.printf("   [Tax Calculation] Digital Order #%d Tax (12%%) = ₹%.2f%n", order.getId(), tax);
    }
}
