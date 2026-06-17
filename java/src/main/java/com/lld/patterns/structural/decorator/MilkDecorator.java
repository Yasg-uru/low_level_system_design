package com.lld.patterns.structural.decorator;

/**
 * Concrete Decorator wrapping a Coffee instance to add milk.
 */
public class MilkDecorator extends BaseDecorator {
    public MilkDecorator(Coffee coffee) {
        super(coffee);
    }

    @Override
    public double getCost() {
        return super.getCost() + 2.0;
    }

    @Override
    public String getDescription() {
        return super.getDescription() + ", milk";
    }
}
