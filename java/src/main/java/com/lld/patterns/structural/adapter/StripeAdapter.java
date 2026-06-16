package com.lld.patterns.structural.adapter;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Concrete Adapter translating Stripe API patterns into standard IPaymentGateway interface.
 */
public class StripeAdapter implements IPaymentGateway {
    private final StripeClient client;

    public StripeAdapter(String secretKey) {
        this.client = new StripeClient(secretKey);
    }

    @Override
    public PaymentOrder createOrder(double amount, String currency, OrderMetadata metadata) {
        int amountInSmallestUnit = (int) Math.round(amount * 100);

        Map<String, Object> params = new HashMap<>();
        params.put("amount", amountInSmallestUnit);
        params.put("currency", currency.toLowerCase());

        Map<String, String> stripeMetadata = new HashMap<>();
        stripeMetadata.put("userId", metadata.getUserId());
        stripeMetadata.put("description", metadata.getDescription());
        stripeMetadata.put("receipt_id", metadata.getReceiptId());
        params.put("metadata", stripeMetadata);

        StripeClient.StripePaymentIntentResponseMock intent = client.paymentIntents.create(params);

        return new PaymentOrder(
            intent.id,
            intent.amount / 100.0,
            intent.currency.toUpperCase(),
            "created",
            new Date(intent.created * 1000)
        );
    }

    @Override
    public PaymentCapture capturePayment(String orderId, String paymentId) {
        StripeClient.StripePaymentIntentResponseMock intent = client.paymentIntents.confirm(orderId);

        String transactionId = orderId;
        if (intent.charges != null && !intent.charges.isEmpty()) {
            transactionId = intent.charges.get(0).id;
        }

        return new PaymentCapture(
            "succeeded".equalsIgnoreCase(intent.status),
            transactionId,
            new Date()
        );
    }

    @Override
    public RefundResult refund(String paymentId, double amount, String reason) {
        Map<String, Object> params = new HashMap<>();
        params.put("charge", paymentId);
        params.put("amount", (int) Math.round(amount * 100));
        params.put("reason", "requested_by_customer");

        Map<String, Object> refund = client.refunds.create(params);

        Integer refundAmount = (Integer) refund.get("amount");
        double refundedRupees = (refundAmount != null ? refundAmount : 0) / 100.0;

        return new RefundResult(
            (String) refund.get("id"),
            refundedRupees,
            "succeeded".equals(refund.get("status")) ? "processed" : "pending"
        );
    }

    @Override
    public PaymentStatus getPaymentStatus(String paymentId) {
        StripeClient.StripePaymentIntentResponseMock intent = client.paymentIntents.retrieve(paymentId);
        
        if ("requires_payment_method".equalsIgnoreCase(intent.status)) return PaymentStatus.CREATED;
        if ("requires_confirmation".equalsIgnoreCase(intent.status)) return PaymentStatus.AUTHORIZED;
        if ("succeeded".equalsIgnoreCase(intent.status)) return PaymentStatus.CAPTURED;
        if ("canceled".equalsIgnoreCase(intent.status)) return PaymentStatus.FAILED;
        return PaymentStatus.FAILED;
    }
}
