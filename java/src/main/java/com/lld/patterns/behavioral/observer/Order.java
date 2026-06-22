package com.lld.patterns.behavioral.observer;

import java.util.List;

/**
 * Record representing a client Order.
 */
public record Order(String id, List<String> items, double totalAmount) {}
