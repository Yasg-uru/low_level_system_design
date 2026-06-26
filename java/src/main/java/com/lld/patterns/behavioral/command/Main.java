package com.lld.patterns.behavioral.command;

import java.util.ArrayList;
import java.util.List;

/**
 * Driver client demonstrating the Command Design Pattern in Java.
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("=============================================================");
        System.out.println("=== Command Design Pattern: Transaction & Batch Queue (Java) ===");
        System.out.println("=============================================================\n");

        try {
            OrderService orderService = new OrderService();
            EmailService emailService = new EmailService();
            CommandInvoker invoker = new CommandInvoker();

            List<CartItem> cartItems = new ArrayList<>();
            cartItems.add(new CartItem("item_prod_1", 3));

            System.out.println("--- 1. Immediate Execution Path ---");
            CommandResult placeResult = invoker.execute(
                    new PlaceOrderCommand(orderService, "user_1", cartItems)
            );

            Order createdOrder = (Order) placeResult.getData();

            CommandResult discountResult = invoker.execute(
                    new ApplyDiscountCommand(orderService, createdOrder.getId(), 20)
            );

            System.out.println("\n--- 2. Reversion (Undo) Flow ---");
            if (invoker.canUndo()) {
                invoker.undo(); // Reverts discount securely back to base scale
            }

            System.out.println("\n--- 3. Job Queuing Batch Processing ---");
            List<String> orderIds = new ArrayList<>();
            orderIds.add(createdOrder.getId());
            invoker.enqueue(new BulkUpdateOrderStatusCommand(orderService, orderIds, OrderStatus.SHIPPED));

            List<String> userIds = new ArrayList<>();
            userIds.add("user_1");
            invoker.enqueue(new SendMarketingEmailCommand(emailService, userIds, "diwali_sale"));

            // Flush the processing pool loops
            invoker.flushQueue();

            System.out.println("\n--- 4. Full Internal History Audit Trails ---");
            System.out.println(invoker.getHistory());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
