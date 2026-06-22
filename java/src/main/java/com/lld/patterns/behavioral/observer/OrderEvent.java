package com.lld.patterns.behavioral.observer;

import java.time.Instant;

/**
 * Record representing an event related to an Order.
 */
public record OrderEvent(OrderEventType type, Order order, Instant timestamp) {}
