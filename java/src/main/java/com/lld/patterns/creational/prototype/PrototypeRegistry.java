package com.lld.patterns.creational.prototype;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Central cached inventory manager for prototype blueprints.
 *
 * @param <T> The type of prototype stored in this registry
 */
public class PrototypeRegistry<T extends IPrototype<T>> {
    private final Map<String, T> prototypes = new HashMap<>();

    public void registerPrototype(String name, T prototype) {
        prototypes.put(name, prototype);
    }

    public T getPrototype(String name) {
        T prototype = prototypes.get(name);
        return prototype != null ? prototype.clonePrototype() : null;
    }

    public boolean has(String name) {
        return prototypes.containsKey(name);
    }

    public List<String> list() {
        return new ArrayList<>(prototypes.keySet());
    }

    public boolean deregister(String name) {
        return prototypes.remove(name) != null;
    }

    public boolean updatePrototype(String name, T prototype) {
        if (!prototypes.containsKey(name)) {
            return false;
        }
        prototypes.put(name, prototype);
        return true;
    }
}
