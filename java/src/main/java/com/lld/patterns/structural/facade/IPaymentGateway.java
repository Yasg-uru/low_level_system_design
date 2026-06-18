package com.lld.patterns.structural.facade;

import java.util.Map;

public interface IPaymentGateway {
    Map<String, Object> createOrder(double amount, String currency, Map<String, Object> metadata) throws Exception;
    Map<String, Object> capturePayment(String orderId, String paymentId) throws Exception;
    void refund(String paymentId, double amount, String reason) throws Exception;
}
