package com.lld.patterns.behavioral.command;

import java.util.List;

/**
 * Command to place a new order.
 */
public class PlaceOrderCommand implements ICommand {
    private final OrderService orderService;
    private final String userId;
    private final List<CartItem> items;
    private String createdOrderId;

    public PlaceOrderCommand(OrderService orderService, String userId, List<CartItem> items) {
        this.orderService = orderService;
        this.userId = userId;
        this.items = items;
    }

    @Override
    public CommandResult execute() throws Exception {
        try {
            Order order = orderService.create(userId, items);
            this.createdOrderId = order.getId();
            return CommandResult.ofSuccess(order);
        } catch (Exception e) {
            return CommandResult.ofFailure(e.getMessage());
        }
    }

    @Override
    public void undo() throws Exception {
        if (createdOrderId == null) {
            throw new IllegalStateException("Nothing to undo — execute() was never called");
        }
        orderService.cancel(createdOrderId);
    }

    @Override
    public boolean canUndo() {
        return createdOrderId != null;
    }

    @Override
    public String getDescription() {
        return "Place order for user " + userId + " — " + items.size() + " items";
    }
}
