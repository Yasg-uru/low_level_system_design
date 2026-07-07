package com.lld.patterns.behavioral.interpreter;

public class StatusEqualsExpression implements IExpression {
    private final String expectedStatus;

    public StatusEqualsExpression(String expectedStatus) {
        this.expectedStatus = expectedStatus;
    }

    @Override
    public boolean interpret(OrderContext context) {
        return context.getStatus().equals(expectedStatus);
    }
}