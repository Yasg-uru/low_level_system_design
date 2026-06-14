package com.lld.patterns.creational.singleton;

/**
 * Singleton Pattern: Lazy Initialization
 * Instance created only when first needed.
 *
 * Thread-safe using synchronized block.
 */
public class LazyInitializedSingleton {
    private static LazyInitializedSingleton instance;

    private LazyInitializedSingleton() {
        System.out.println("[LazyInitializedSingleton] Instance created");
    }

    public static synchronized LazyInitializedSingleton getInstance() {
        if (instance == null) {
            instance = new LazyInitializedSingleton();
        }
        return instance;
    }

    public void doSomething() {
        System.out.println("[LazyInitializedSingleton] Doing something...");
    }
}
