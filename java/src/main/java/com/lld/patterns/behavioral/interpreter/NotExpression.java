package com.lld.patterns.behavioral.interpreter;

public class NotExpression implements IExpression {
    private final IExpression expression;

    public NotExpression(IExpression expression) {
        this.expression = expression;
    }

    @Override
    public boolean interpret(OrderContext context) {
        return !expression.interpret(context);
    }
}