package com.lld.oops.polymorphism;

/**
 * Circle implementation of Shape.
 */
public class Circle implements Shape {
    private double radius;

    public Circle(double radius) {
        this.radius = radius;
    }

    @Override
    public double calculateArea() {
        return Math.PI * radius * radius;
    }

    @Override
    public double calculatePerimeter() {
        return 2 * Math.PI * radius;
    }

    @Override
    public String getDescription() {
        return "Circle with radius " + radius;
    }
}
