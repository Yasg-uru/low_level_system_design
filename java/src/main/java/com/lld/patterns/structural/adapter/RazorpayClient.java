package com.lld.patterns.structural.adapter;

import java.util.HashMap;
import java.util.Map;

/**
 * Mock Razorpay SDK Client (Adaptee).
 */
public class RazorpayClient {
    public RazorpayClient(String keyId, String keySecret) {}

    public static class RazorpayOrderResponseMock {
        public String id = "rzp_order_99211";
        public String entity = "order";
        public int amount; // In Paise
        public int amount_paid = 0;
        public int amount_due;
        public String currency;
        public String receipt;
        public String status = "created";
        public long created_at = System.currentTimeMillis() / 1000;
    }

    public static class RazorpayPaymentResponseMock {
        public String id;
        public int amount = 500000; // 5000 INR in paise
        public String currency = "INR";
        public String status = "authorized";
        public String order_id = "rzp_order_99211";
        public boolean captured = false;
        public long created_at = System.currentTimeMillis() / 1000;
    }

    public static class Orders {
        public RazorpayOrderResponseMock create(Map<String, Object> data) {
            RazorpayOrderResponseMock response = new RazorpayOrderResponseMock();
            response.amount = (Integer) data.get("amount");
            response.amount_due = response.amount;
            response.currency = (String) data.get("currency");
            response.receipt = (String) data.get("receipt");
            return response;
        }
    }

    public static class Payments {
        public RazorpayPaymentResponseMock fetch(String id) {
            RazorpayPaymentResponseMock response = new RazorpayPaymentResponseMock();
            response.id = id;
            return response;
        }

        public Map<String, Object> capture(String id, int amount) {
            Map<String, Object> res = new HashMap<>();
            res.put("status", "captured");
            return res;
        }

        public Map<String, Object> refund(String id, Map<String, Object> options) {
            Map<String, Object> res = new HashMap<>();
            res.put("id", "rfnd_001");
            res.put("amount", options.get("amount"));
            res.put("status", "processed");
            return res;
        }
    }

    public Orders orders = new Orders();
    public Payments payments = new Payments();
}
