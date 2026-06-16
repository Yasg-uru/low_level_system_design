package com.lld.patterns.creational.prototype;

/**
 * Interface for objects that support cloning (Prototype Pattern).
 * This replaces or wraps Java's built-in Cloneable interface to provide type-safe cloning.
 *
 * @param <T> The type of object that is being cloned
 */
public interface IPrototype<T> {
    /**
     * Performs a copy (typically a deep copy) of the prototype instance.
     *
     * @return A copy of the object
     */
    T clonePrototype();
}
