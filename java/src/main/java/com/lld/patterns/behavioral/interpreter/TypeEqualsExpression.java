package com.lld.patterns.behavioral.interpreter;

public class TypeEqualsExpression implements IExpression {
    private final String expectedType;

    public TypeEqualsExpression(String expectedType) {
        this.expectedType = expectedType;
    }

    @Override
    public boolean interpret(OrderContext context) {
        return context.getType().equals(expectedType);
    }
}