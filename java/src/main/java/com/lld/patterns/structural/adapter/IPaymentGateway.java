package com.lld.patterns.structural.adapter;

/**
 * Target Interface representing standard payment gateway interactions.
 */
public interface IPaymentGateway {
    PaymentOrder createOrder(double amount, String currency, OrderMetadata metadata) throws Exception;
    PaymentCapture capturePayment(String orderId, String paymentId) throws Exception;
    RefundResult refund(String paymentId, double amount, String reason) throws Exception;
    PaymentStatus getPaymentStatus(String paymentId) throws Exception;
}
