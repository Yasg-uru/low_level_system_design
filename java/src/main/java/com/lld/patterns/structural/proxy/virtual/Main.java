package com.lld.patterns.structural.proxy.virtual;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Virtual Proxy Demo ===");

        System.out.println("Creating proxy (no expensive operation yet):");
        Image image1 = new ProxyImage("photo1.jpg");
        Image image2 = new ProxyImage("photo2.jpg");

        System.out.println("\nFirst display of image 1 - triggers expensive loading:");
        image1.display();

        System.out.println("\nSecond display of image 1 - uses cached object:");
        image1.display();

        System.out.println("\nDisplaying second image - triggers its loading:");
        image2.display();

        System.out.println("\nSecond image displayed again - uses cached object:");
        image2.display();
    }
}
