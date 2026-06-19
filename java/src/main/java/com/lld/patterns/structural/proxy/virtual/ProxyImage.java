package com.lld.patterns.structural.proxy.virtual;

public class ProxyImage implements Image {
    private final String filename;
    private RealImage realImage = null;

    public ProxyImage(String filename) {
        this.filename = filename;
    }

    @Override
    public void display() {
        if (realImage == null) {
            System.out.println("Creating RealImage on first access...");
            realImage = new RealImage(filename);
        }
        realImage.display();
    }
}
