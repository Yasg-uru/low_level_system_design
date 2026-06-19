package com.lld.patterns.structural.proxy.virtual;

public class RealImage implements Image {
    private final String filename;

    public RealImage(String filename) {
        this.filename = filename;
        this.loadFromDisk();
    }

    private void loadFromDisk() {
        System.out.println("Loading " + this.filename + " from disk... (expensive operation)");
        try {
            // Simulate load delay
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println(this.filename + " loaded.");
    }

    @Override
    public void display() {
        System.out.println("Displaying " + this.filename);
    }
}
