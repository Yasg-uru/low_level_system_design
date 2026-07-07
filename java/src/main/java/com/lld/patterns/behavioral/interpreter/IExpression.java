package com.lld.patterns.behavioral.interpreter;

public interface IExpression {
    boolean interpret(OrderContext context);
}