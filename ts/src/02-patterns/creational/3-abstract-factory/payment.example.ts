interface paymentService {
    createPayment(amount: number): void;
}
interface refundService {
    createRefund(transactionId: string): void;
}
interface webhookService {
    verifyWebhook(signature: string): boolean;
}



class StripePaymentService implements paymentService {
    createPayment(amount: number): void {
        console.log(`Creating Stripe payment of amount: ${amount}`);
    }
}

class StripeRefundService implements refundService {
    createRefund(transactionId: string): void {
        console.log(`Creating Stripe refund for transaction ID: ${transactionId}`);
    }
}

class StripeWebhookService implements webhookService {
    verifyWebhook(signature: string): boolean {
        console.log(`Verifying Stripe webhook with signature: ${signature}`);
        return true;
    }
}

class PayPalPaymentService implements paymentService {
    createPayment(amount: number): void {
        console.log(`Creating PayPal payment of amount: ${amount}`);
    }
}

class PayPalRefundService implements refundService {
    createRefund(transactionId: string): void {
        console.log(`Creating PayPal refund for transaction ID: ${transactionId}`);
    }
}

class PayPalWebhookService implements webhookService {
    verifyWebhook(signature: string): boolean {
        console.log(`Verifying PayPal webhook with signature: ${signature}`);
        return true;
    }
}

interface PaymentFactory {
    createPaymentService(): paymentService;
    createRefundService(): refundService;
    createWebhookService(): webhookService;
}

class StripeFactory implements PaymentFactory {
    createPaymentService(): paymentService {
        return new StripePaymentService();
    }
    createRefundService(): refundService {
        return new StripeRefundService();
    }
    createWebhookService(): webhookService {
        return new StripeWebhookService();
    }
}

class PayPalFactory implements PaymentFactory {
    createPaymentService(): paymentService {
        return new PayPalPaymentService();
    }
    createRefundService(): refundService {
        return new PayPalRefundService();
    }
    createWebhookService(): webhookService {
        return new PayPalWebhookService();
    }
}

// Usage
const stripeFactory = new StripeFactory();
const stripePayment = stripeFactory.createPaymentService();
stripePayment.createPayment(100);

const paypalFactory = new PayPalFactory();
const paypalRefund = paypalFactory.createRefundService();
paypalRefund.createRefund("txn_12345");



// in backend we can use the factory to create services based on the payment provider
let paymentProvider: PaymentFactory;
if(process.env.PAYMENT_PROVIDER === "stripe") {
    paymentProvider = new StripeFactory();
} else {
    paymentProvider = new PayPalFactory();
}

const paymentService = paymentProvider.createPaymentService();
paymentService.createPayment(200); // so basically it will adjust the payment provider based on the environment variable and create the appropriate service. so user dont need to change code in future if they needs to switch to the different payment provider.