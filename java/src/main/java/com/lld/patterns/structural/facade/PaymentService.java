package com.lld.patterns.structural.facade;

import java.util.HashMap;
import java.util.Map;

public class PaymentService {
    private final IPaymentGateway gateway;

    public PaymentService(IPaymentGateway gateway) {
        this.gateway = gateway;
    }

    public PaymentResult charge(double amount, String userId) throws Exception {
        Map<String, Object> metadata = new HashMap<>();
        metadata.put("userId", userId);
        metadata.put("description", "Order checkout");
        metadata.put("receiptId", "rcpt_" + System.currentTimeMillis());

        Map<String, Object> order = gateway.createOrder(amount, "INR", metadata);
        String orderId = (String) order.get("id");

        Map<String, Object> capture = gateway.capturePayment(orderId, orderId);
        
        boolean success = false;
        if (capture.containsKey("status")) {
            success = "captured".equals(capture.get("status"));
        } else if (capture.containsKey("success")) {
            success = (Boolean) capture.get("success");
        }

        String transactionId = (String) capture.get("id");
        if (transactionId == null) {
            transactionId = (String) capture.get("transactionId");
        }

        return new PaymentResult(success, transactionId, amount);
    }

    public void refund(String transactionId, double amount, String reason) throws Exception {
        gateway.refund(transactionId, amount, reason);
    }
}
