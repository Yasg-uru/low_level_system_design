package com.lld.patterns.structural.decorator;

/**
 * Concrete Decorator wrapping a Coffee instance to add whipped cream.
 */
public class WhippedCreamDecorator extends BaseDecorator {
    public WhippedCreamDecorator(Coffee coffee) {
        super(coffee);
    }

    @Override
    public double getCost() {
        return super.getCost() + 3.0;
    }

    @Override
    public String getDescription() {
        return super.getDescription() + ", whipped cream";
    }
}
