package com.lld.patterns.structural.decorator;

/**
 * Concrete Decorator wrapping a Coffee instance to add sugar.
 */
public class SugarDecorator extends BaseDecorator {
    public SugarDecorator(Coffee coffee) {
        super(coffee);
    }

    @Override
    public double getCost() {
        return super.getCost() + 1.0;
    }

    @Override
    public String getDescription() {
        return super.getDescription() + ", sugar";
    }
}
