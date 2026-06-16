package com.lld.patterns.structural.adapter;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Concrete Adapter translating Razorpay API patterns into standard IPaymentGateway interface.
 */
public class RazorpayAdapter implements IPaymentGateway {
    private final RazorpayClient client;

    public RazorpayAdapter(String keyId, String keySecret) {
        this.client = new RazorpayClient(keyId, keySecret);
    }

    @Override
    public PaymentOrder createOrder(double amount, String currency, OrderMetadata metadata) {
        int amountInPaise = (int) Math.round(amount * 100);

        Map<String, Object> params = new HashMap<>();
        params.put("amount", amountInPaise);
        params.put("currency", currency.toUpperCase());
        params.put("receipt", metadata.getReceiptId());

        Map<String, String> notes = new HashMap<>();
        notes.put("userId", metadata.getUserId());
        notes.put("description", metadata.getDescription());
        params.put("notes", notes);

        RazorpayClient.RazorpayOrderResponseMock razorpayOrder = client.orders.create(params);

        return new PaymentOrder(
            razorpayOrder.id,
            razorpayOrder.amount / 100.0, // paise -> rupees
            razorpayOrder.currency,
            mapOrderStatus(razorpayOrder.status),
            new Date(razorpayOrder.created_at * 1000)
        );
    }

    @Override
    public PaymentCapture capturePayment(String orderId, String paymentId) {
        RazorpayClient.RazorpayPaymentResponseMock payment = client.payments.fetch(paymentId);
        client.payments.capture(paymentId, payment.amount);

        return new PaymentCapture(
            true,
            paymentId,
            new Date()
        );
    }

    @Override
    public RefundResult refund(String paymentId, double amount, String reason) {
        int amountInPaise = (int) Math.round(amount * 100);
        Map<String, Object> options = new HashMap<>();
        options.put("amount", amountInPaise);
        
        Map<String, String> notes = new HashMap<>();
        notes.put("reason", reason);
        options.put("notes", notes);

        Map<String, Object> refund = client.payments.refund(paymentId, options);

        return new RefundResult(
            (String) refund.get("id"),
            ((Integer) refund.get("amount")) / 100.0,
            "processed".equals(refund.get("status")) ? "processed" : "pending"
        );
    }

    @Override
    public PaymentStatus getPaymentStatus(String paymentId) {
        RazorpayClient.RazorpayPaymentResponseMock payment = client.payments.fetch(paymentId);
        return mapPaymentStatus(payment.status);
    }

    private String mapOrderStatus(String status) {
        if ("created".equalsIgnoreCase(status)) return "created";
        if ("attempted".equalsIgnoreCase(status)) return "attempted";
        if ("paid".equalsIgnoreCase(status)) return "paid";
        return "created";
    }

    private PaymentStatus mapPaymentStatus(String status) {
        if ("created".equalsIgnoreCase(status)) return PaymentStatus.CREATED;
        if ("authorized".equalsIgnoreCase(status)) return PaymentStatus.AUTHORIZED;
        if ("captured".equalsIgnoreCase(status)) return PaymentStatus.CAPTURED;
        if ("refunded".equalsIgnoreCase(status)) return PaymentStatus.REFUNDED;
        return PaymentStatus.FAILED;
    }
}
