package com.lld.patterns.structural.adapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Driver program executing checkout processes utilizing adapters.
 */
public class Main {
    public static void main(String[] args) {
        IOrderRepository repo = new PostgresOrderRepository();
        INotificationService logs = new EmailNotificationService();
        
        List<CartItem> sampleCart = new ArrayList<>();
        sampleCart.add(new CartItem("sku_772", "Full-Stack System Architecture Guide", 2499.0, 2));

        System.out.println("=== Initializing Application Integration Matrix ===");

        // Switch Adapters dynamically at the composition root
        IPaymentGateway rzpAdapter = new RazorpayAdapter("rzp_live_key", "rzp_secret_hash");
        OrderService checkoutEngine = new OrderService(repo, rzpAdapter, logs);

        try {
            Order checkoutTxn = checkoutEngine.checkout("usr_yash_choudhary", sampleCart);
            System.out.println("\n[Core Application] Order Session Initialized -> App ID: " + checkoutTxn.getId() 
                    + " | External Gateway Reference: " + checkoutTxn.getPaymentOrderId());
            System.out.println("                   Calculated Ledger Amount: Rs." + checkoutTxn.getTotal());

            checkoutEngine.confirmPayment(checkoutTxn.getId(), "pay_capture_txn_88192");
            System.out.println("\n=== Integration Lifecycle Operations Concluded Successfully ===");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
