package com.lld.oops;

import com.lld.oops.abstraction.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Abstraction Tests")
class AbstractionTest {

    @Test
    @DisplayName("Car should start when fuel available")
    void testCarStart() {
        Vehicle car = new Car();
        car.refuel(50);
        car.start();

        assertTrue(car.isRunning());
    }

    @Test
    @DisplayName("Car should not start without fuel")
    void testCarStartNoFuel() {
        Vehicle car = new Car();
        car.start();

        assertFalse(car.isRunning());
    }

    @Test
    @DisplayName("Motorcycle should accelerate when running")
    void testMotorcycleAccelerate() {
        Vehicle bike = new Motorcycle();
        bike.refuel(20);
        bike.start();
        bike.accelerate(80);

        assertTrue(bike.isRunning());
    }

    @Test
    @DisplayName("Stripe payment should process successfully")
    void testStripePayment() {
        IPaymentProcessor processor = new StripePayment();
        boolean result = processor.processPayment(99.99);

        assertTrue(result);
    }

    @Test
    @DisplayName("PayPal payment should process successfully")
    void testPayPalPayment() {
        IPaymentProcessor processor = new PayPalPayment();
        boolean result = processor.processPayment(50.00);

        assertTrue(result);
    }

    @Test
    @DisplayName("Both payment processors should implement same interface")
    void testPaymentProcessorPolymorphism() {
        IPaymentProcessor stripe = new StripePayment();
        IPaymentProcessor paypal = new PayPalPayment();

        assertTrue(stripe.processPayment(100));
        assertTrue(paypal.processPayment(100));
    }
}
