package com.lld.oops.abstraction;

/**
 * Another concrete implementation of Vehicle.
 * Demonstrates same interface, different implementation.
 */
public class Motorcycle extends Vehicle {
    private int currentSpeed;

    public Motorcycle() {
        super();
        this.currentSpeed = 0;
    }

    @Override
    public void start() {
        if (fuelLevel > 0) {
            isRunning = true;
            System.out.println("🏍️  Motorcycle engine started");
        }
    }

    @Override
    public void stop() {
        isRunning = false;
        currentSpeed = 0;
        System.out.println("🏍️  Motorcycle stopped");
    }

    @Override
    public void accelerate(int speed) {
        if (isRunning && fuelLevel > 0) {
            currentSpeed = speed;
            fuelLevel -= speed * 0.005;  // More fuel-efficient than car
            System.out.println("🏍️  Motorcycle accelerating to " + speed + " km/h");
        }
    }

    public int getCurrentSpeed() {
        return currentSpeed;
    }
}
