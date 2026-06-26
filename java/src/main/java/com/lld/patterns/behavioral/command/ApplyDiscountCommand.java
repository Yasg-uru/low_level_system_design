package com.lld.patterns.behavioral.command;

import java.util.HashMap;
import java.util.Map;

/**
 * Command to apply a discount to an existing order.
 */
public class ApplyDiscountCommand implements ICommand {
    private final OrderService orderService;
    private final String orderId;
    private final double discountPercent;
    private Double previousTotal;

    public ApplyDiscountCommand(OrderService orderService, String orderId, double discountPercent) {
        this.orderService = orderService;
        this.orderId = orderId;
        this.discountPercent = discountPercent;
    }

    @Override
    public CommandResult execute() throws Exception {
        Order order = orderService.findById(orderId);
        if (order == null) {
            return CommandResult.ofFailure("Order not found");
        }

        this.previousTotal = order.getTotal();
        double discounted = order.getTotal() * (1 - discountPercent / 100);
        orderService.updateTotal(orderId, discounted);

        Map<String, Double> payload = new HashMap<>();
        payload.put("previous", previousTotal);
        payload.put("new", discounted);

        return CommandResult.ofSuccess(payload);
    }

    @Override
    public void undo() throws Exception {
        if (previousTotal == null) {
            throw new IllegalStateException("Nothing to undo");
        }
        orderService.updateTotal(orderId, previousTotal);
    }

    @Override
    public boolean canUndo() {
        return previousTotal != null;
    }

    @Override
    public String getDescription() {
        return "Apply " + discountPercent + "% discount to order " + orderId;
    }
}
