package com.lld.patterns.structural.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Mock Stripe SDK Client (Adaptee).
 */
public class StripeClient {
    public StripeClient(String secretKey) {}

    public static class Charge {
        public String id;
        public boolean captured;

        public Charge(String id, boolean captured) {
            this.id = id;
            this.captured = captured;
        }
    }

    public static class StripePaymentIntentResponseMock {
        public String id = "pi_stripe_11204";
        public int amount;
        public String currency;
        public String status = "requires_payment_method";
        public String client_secret = "secret_sec_9912";
        public long created = System.currentTimeMillis() / 1000;
        public List<Charge> charges = new ArrayList<>();
    }

    public static class PaymentIntents {
        public StripePaymentIntentResponseMock create(Map<String, Object> data) {
            StripePaymentIntentResponseMock response = new StripePaymentIntentResponseMock();
            response.amount = (Integer) data.get("amount");
            response.currency = (String) data.get("currency");
            return response;
        }

        public StripePaymentIntentResponseMock confirm(String id) {
            StripePaymentIntentResponseMock response = new StripePaymentIntentResponseMock();
            response.id = id;
            response.status = "succeeded";
            response.charges.add(new Charge("ch_001", true));
            return response;
        }

        public StripePaymentIntentResponseMock retrieve(String id) {
            StripePaymentIntentResponseMock response = new StripePaymentIntentResponseMock();
            response.id = id;
            response.status = "succeeded";
            response.charges.add(new Charge("ch_001", true));
            return response;
        }
    }

    public static class Refunds {
        public Map<String, Object> create(Map<String, Object> data) {
            Map<String, Object> response = new HashMap<>();
            response.id = "re_stripe_002";
            response.put("amount", data.get("amount"));
            response.put("status", "succeeded");
            return response;
        }
    }

    public PaymentIntents paymentIntents = new PaymentIntents();
    public Refunds refunds = new Refunds();
}
