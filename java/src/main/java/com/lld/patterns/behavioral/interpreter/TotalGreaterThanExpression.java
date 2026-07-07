package com.lld.patterns.behavioral.interpreter;

public class TotalGreaterThanExpression implements IExpression {
    private final double threshold;

    public TotalGreaterThanExpression(double threshold) {
        this.threshold = threshold;
    }

    @Override
    public boolean interpret(OrderContext context) {
        return context.getTotal() > threshold;
    }
}