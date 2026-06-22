package com.lld.patterns.behavioral.strategy;

/**
 * Strategy interface declaring the payment execution contract.
 */
public interface IPaymentStrategy {
    void processPayment(double amount);
}
