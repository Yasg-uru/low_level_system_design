package com.lld.patterns.structural.decorator;

/**
 * Concrete Component representing the base coffee product.
 */
public class SimpleCoffee implements Coffee {
    @Override
    public double getCost() {
        return 5.0;
    }

    @Override
    public String getDescription() {
        return "Simple coffee";
    }
}
