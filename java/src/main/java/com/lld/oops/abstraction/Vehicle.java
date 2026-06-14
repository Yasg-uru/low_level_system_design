package com.lld.oops.abstraction;

/**
 * Abstract class demonstrating abstraction.
 *
 * Hides implementation details and exposes only essential features.
 * Subclasses must implement abstract methods.
 */
public abstract class Vehicle {
    protected double fuelLevel;
    protected boolean isRunning;

    public Vehicle() {
        this.fuelLevel = 0;
        this.isRunning = false;
    }

    // Abstract methods - must be implemented by subclasses
    public abstract void start();
    public abstract void stop();
    public abstract void accelerate(int speed);

    // Concrete method - same for all vehicles
    public void refuel(double amount) {
        this.fuelLevel += amount;
        System.out.println("Refueled with " + amount + "L. Current fuel: " + fuelLevel + "L");
    }

    public String getStatus() {
        return "Status: " + (isRunning ? "Running" : "Stopped") + ", Fuel: " + fuelLevel + "L";
    }

    public double getFuelLevel() {
        return fuelLevel;
    }

    public boolean isRunning() {
        return isRunning;
    }
}
