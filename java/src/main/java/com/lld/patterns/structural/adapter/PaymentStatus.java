package com.lld.patterns.structural.adapter;

/**
 * Supported payment gateway payment states.
 */
public enum PaymentStatus {
    CREATED,
    AUTHORIZED,
    CAPTURED,
    REFUNDED,
    FAILED
}
