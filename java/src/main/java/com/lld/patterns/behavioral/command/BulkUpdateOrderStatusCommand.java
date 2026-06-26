package com.lld.patterns.behavioral.command;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Command to update the status of multiple orders in bulk.
 */
public class BulkUpdateOrderStatusCommand implements ICommand {
    private final OrderService orderService;
    private final List<String> orderIds;
    private final OrderStatus newStatus;
    private final Map<String, OrderStatus> previousStatuses = new HashMap<>();

    public BulkUpdateOrderStatusCommand(OrderService orderService, List<String> orderIds, OrderStatus newStatus) {
        this.orderService = orderService;
        this.orderIds = orderIds;
        this.newStatus = newStatus;
    }

    @Override
    public CommandResult execute() throws Exception {
        for (String id : orderIds) {
            Order order = orderService.findById(id);
            if (order != null) {
                previousStatuses.put(id, order.getStatus());
            }
        }

        for (String id : orderIds) {
            orderService.updateStatus(id, newStatus);
        }

        Map<String, Integer> payload = new HashMap<>();
        payload.put("updated", orderIds.size());

        return CommandResult.ofSuccess(payload);
    }

    @Override
    public void undo() throws Exception {
        for (Map.Entry<String, OrderStatus> entry : previousStatuses.entrySet()) {
            orderService.updateStatus(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public boolean canUndo() {
        return !previousStatuses.isEmpty();
    }

    @Override
    public String getDescription() {
        return "Bulk update " + orderIds.size() + " orders to status \"" + newStatus + "\"";
    }
}
