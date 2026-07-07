package com.lld.patterns.behavioral.interpreter;

public class AndExpression implements IExpression {
    private final IExpression leftExpression;
    private final IExpression rightExpression;

    public AndExpression(IExpression leftExpression, IExpression rightExpression) {
        this.leftExpression = leftExpression;
        this.rightExpression = rightExpression;
    }

    @Override
    public boolean interpret(OrderContext context) {
        return leftExpression.interpret(context) && rightExpression.interpret(context);
    }
}