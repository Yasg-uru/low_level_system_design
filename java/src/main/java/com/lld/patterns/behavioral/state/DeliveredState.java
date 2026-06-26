package com.lld.patterns.behavioral.state;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * State representing a delivered order with a 30-day return eligibility logic.
 */
public class DeliveredState implements IOrderState {
    private final LocalDateTime deliveredDate;

    public DeliveredState() {
        this.deliveredDate = LocalDateTime.now();
    }

    // Constructor that allows setting custom time (e.g. for testing old delivery dates)
    public DeliveredState(LocalDateTime deliveredDate) {
        this.deliveredDate = deliveredDate;
    }

    @Override
    public void confirm(Order order) {
        throw new IllegalStateException("Order is already delivered.");
    }

    @Override
    public void ship(Order order) {
        throw new IllegalStateException("Order is already delivered.");
    }

    @Override
    public void deliver(Order order) {
        throw new IllegalStateException("Order is already delivered.");
    }

    @Override
    public void cancel(Order order) {
        throw new IllegalStateException("Cannot cancel a delivered order.");
    }

    @Override
    public void requestReturn(Order order) {
        LocalDateTime currentDate = LocalDateTime.now();
        long diffInDays = Duration.between(deliveredDate, currentDate).toDays();

        if (diffInDays <= 30) {
            System.out.println("-> Return request accepted within valid eligibility windows.");
            order.setState(new ReturnedState());
        } else {
            throw new IllegalStateException("Return period has expired. Cannot request return.");
        }
    }

    @Override
    public String getStatus() {
        return "Delivered";
    }

    @Override
    public List<String> getAllowedActions() {
        LocalDateTime currentDate = LocalDateTime.now();
        long diffInDays = Duration.between(deliveredDate, currentDate).toDays();

        return diffInDays <= 30 ? Arrays.asList("requestReturn") : Collections.emptyList();
    }
}
