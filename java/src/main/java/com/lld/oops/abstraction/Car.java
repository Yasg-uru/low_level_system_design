package com.lld.oops.abstraction;

/**
 * Concrete implementation of Vehicle.
 * Implements abstract methods from Vehicle class.
 */
public class Car extends Vehicle {
    private int currentSpeed;

    public Car() {
        super();
        this.currentSpeed = 0;
    }

    @Override
    public void start() {
        if (fuelLevel > 0) {
            isRunning = true;
            System.out.println("🚗 Car engine started");
        } else {
            System.out.println("Cannot start: No fuel");
        }
    }

    @Override
    public void stop() {
        isRunning = false;
        currentSpeed = 0;
        System.out.println("🚗 Car stopped");
    }

    @Override
    public void accelerate(int speed) {
        if (isRunning && fuelLevel > 0) {
            currentSpeed = speed;
            fuelLevel -= speed * 0.01;
            System.out.println("🚗 Car accelerating to " + speed + " km/h");
        }
    }

    @Override
    public String getStatus() {
        return super.getStatus() + ", Speed: " + currentSpeed + " km/h";
    }

    public int getCurrentSpeed() {
        return currentSpeed;
    }
}
