package com.lld.patterns.creational.singleton;

/**
 * Singleton Pattern in Java.
 * Ensures only one instance exists throughout the application.
 *
 * Eager initialization approach.
 */
public class Singleton {
    // Single instance created at class loading time
    private static final Singleton INSTANCE = new Singleton();

    // Private constructor prevents direct instantiation
    private Singleton() {
        System.out.println("[Singleton] Instance created");
    }

    // Global access point
    public static Singleton getInstance() {
        return INSTANCE;
    }

    public void doSomething() {
        System.out.println("[Singleton] Doing something...");
    }
}
