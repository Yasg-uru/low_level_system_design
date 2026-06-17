package com.lld.patterns.structural.decorator;

/**
 * Base Decorator implementing the Component interface and holding a reference to it.
 */
public abstract class BaseDecorator implements Coffee {
    protected final Coffee coffee;

    protected BaseDecorator(Coffee coffee) {
        this.coffee = coffee;
    }

    @Override
    public double getCost() {
        return this.coffee.getCost();
    }

    @Override
    public String getDescription() {
        return this.coffee.getDescription();
    }
}
