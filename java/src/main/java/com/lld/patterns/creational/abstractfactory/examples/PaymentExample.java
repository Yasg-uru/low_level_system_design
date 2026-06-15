package com.lld.patterns.creational.abstractfactory.examples;

// ============================================================================
// 1. ABSTRACT PRODUCTS (Interfaces)
// ============================================================================

interface PaymentService {
    void createPayment(double amount);
}

interface RefundService {
    void createRefund(String transactionId);
}

interface WebhookService {
    boolean verifyWebhook(String signature);
}

// ============================================================================
// 2. CONCRETE PRODUCTS: STRIPE IMPLEMENTATION
// ============================================================================

class StripePaymentService implements PaymentService {
    @Override
    public void createPayment(double amount) {
        System.out.println("Creating Stripe payment of amount: " + amount);
    }
}

class StripeRefundService implements RefundService {
    @Override
    public void createRefund(String transactionId) {
        System.out.println("Creating Stripe refund for transaction ID: " + transactionId);
    }
}

class StripeWebhookService implements WebhookService {
    @Override
    public boolean verifyWebhook(String signature) {
        System.out.println("Verifying Stripe webhook with signature: " + signature);
        return true;
    }
}

// ============================================================================
// 3. CONCRETE PRODUCTS: PAYPAL IMPLEMENTATION
// ============================================================================

class PayPalPaymentService implements PaymentService {
    @Override
    public void createPayment(double amount) {
        System.out.println("Creating PayPal payment of amount: " + amount);
    }
}

class PayPalRefundService implements RefundService {
    @Override
    public void createRefund(String transactionId) {
        System.out.println("Creating PayPal refund for transaction ID: " + transactionId);
    }
}

class PayPalWebhookService implements WebhookService {
    @Override
    public boolean verifyWebhook(String signature) {
        System.out.println("Verifying PayPal webhook with signature: " + signature);
        return true;
    }
}

// ============================================================================
// 4. ABSTRACT FACTORY
// ============================================================================

interface PaymentFactory {
    PaymentService createPaymentService();
    RefundService createRefundService();
    WebhookService createWebhookService();
}

// ============================================================================
// 5. CONCRETE FACTORIES
// ============================================================================

class StripeFactory implements PaymentFactory {
    @Override
    public PaymentService createPaymentService() {
        return new StripePaymentService();
    }

    @Override
    public RefundService createRefundService() {
        return new StripeRefundService();
    }

    @Override
    public WebhookService createWebhookService() {
        return new StripeWebhookService();
    }
}

class PayPalFactory implements PaymentFactory {
    @Override
    public PaymentService createPaymentService() {
        return new PayPalPaymentService();
    }

    @Override
    public RefundService createRefundService() {
        return new PayPalRefundService();
    }

    @Override
    public WebhookService createWebhookService() {
        return new PayPalWebhookService();
    }
}

// ============================================================================
// 6. CLIENT RUNNER
// ============================================================================

public class PaymentExample {
    public static void main(String[] args) {
        // Direct factory usage
        PaymentFactory stripeFactory = new StripeFactory();
        PaymentService stripePayment = stripeFactory.createPaymentService();
        stripePayment.createPayment(100.0);

        PaymentFactory paypalFactory = new PayPalFactory();
        RefundService paypalRefund = paypalFactory.createRefundService();
        paypalRefund.createRefund("txn_12345");

        // Environment-based bootstrapping simulation
        String providerEnv = System.getenv("PAYMENT_PROVIDER");
        if (providerEnv == null) {
            providerEnv = "stripe"; // Fallback default
        }

        PaymentFactory paymentProvider;
        if ("stripe".equalsIgnoreCase(providerEnv)) {
            paymentProvider = new StripeFactory();
        } else {
            paymentProvider = new PayPalFactory();
        }

        System.out.println("\n--- Simulated Backend (Provider: " + providerEnv.toUpperCase() + ") ---");
        PaymentService paymentService = paymentProvider.createPaymentService();
        paymentService.createPayment(200.0);
    }
}
