/**
 * ============================================================================
 * DECORATOR DESIGN PATTERN: CROSS-CUTTING CONCERNS FOR PAYMENT GATEWAYS
 * ============================================================================
 * * Purpose: Dynamically attaches extra responsibilities (Logging, Retries, 
 * Telemetry) to an object without altering its code or breaking core contracts.
 * * Structural Layout:
 * - Component Interface: IPaymentGateway (Defines operational contract)
 * - Concrete Component:  BasePaymentGateway (Performs base execution)
 * - Base Decorator:      PaymentGatewayDecorator (Implements interface & wraps instance)
 * - Concrete Decorators: LoggingPaymentGateway, RetryPaymentGateway (Add behavioral wrappers)
 * - Composition Root:   Constructs onion-skin wrapper execution paths.
 */

// ============================================================================
// 1. DOMAIN CORE INTERFACES & STRUCTS
// ============================================================================

export interface OrderMetadata {
    customerId: string;
    items: Array<{ productId: string; quantity: number; price: number }>;
    [key: string]: any; 
}

export interface PaymentOrder {
    id: string;
    amount: number;
    currency: string;
    status: 'created' | 'failed';
    meta: OrderMetadata;
}

export interface PaymentCapture {
    id: string;
    orderId: string;
    amount: number;
    currency: string;
    status: 'captured' | 'failed';
}

export interface RefundResult {
    id: string;
    paymentId: string;
    amount: number;
    reason: string;
    status: 'refunded' | 'failed';
}

export interface PaymentStatus {
    paymentId: string;
    status: 'pending' | 'completed' | 'failed' | 'refunded';
}

export interface Logger {
    log(message: string): void;
    error(message: string, error?: any): void;
}

export interface IPaymentGateway {
    createOrder(amount: number, currency: string, meta: OrderMetadata): Promise<PaymentOrder>;
    capturePayment(orderId: string, paymentId: string): Promise<PaymentCapture>;
    refund(paymentId: string, amount: number, reason: string): Promise<RefundResult>;
    getPaymentStatus(paymentId: string): Promise<PaymentStatus>;
}

// ============================================================================
// 2. CONCRETE INFRASTRUCTURE UTILITIES
// ============================================================================

export class ConsoleLogger implements Logger {
    public log(message: string): void {
        console.log(`[LOG] [${new Date().toISOString()}] ${message}`);
    }
    public error(message: string, error?: any): void {
        console.error(`[ERROR] [${new Date().toISOString()}] ${message}`, error ?? '');
    }
}

// ============================================================================
// 3. BASE CONCRETE COMPONENT (The Core Execution Node)
// ============================================================================

export class BasePaymentGateway implements IPaymentGateway {
    // Injectable fault triggers for simulation purposes
    private simulateFailureOnce = false;

    public enableTransientFailureSimulation(): void {
        this.simulateFailureOnce = true;
    }

    public async createOrder(amount: number, currency: string, meta: OrderMetadata): Promise<PaymentOrder> {
        this.evaluateTransientFault();
        return {
            id: 'order_' + Math.random().toString(36).substring(2, 11),
            amount,
            currency,
            status: 'created',
            meta
        };
    }

    public async capturePayment(orderId: string, paymentId: string): Promise<PaymentCapture> {
        return {
            id: 'capture_' + Math.random().toString(36).substring(2, 11),
            orderId,
            amount: 100,
            currency: 'USD',
            status: 'captured'
        };
    }

    public async refund(paymentId: string, amount: number, reason: string): Promise<RefundResult> {
        return {
            id: 'refund_' + Math.random().toString(36).substring(2, 11),
            paymentId,
            amount,
            reason,
            status: 'refunded'
        };
    }

    public async getPaymentStatus(paymentId: string): Promise<PaymentStatus> {
        return {
            paymentId,
            status: 'completed'
        };
    }

    private evaluateTransientFault(): void {
        if (this.simulateFailureOnce) {
            this.simulateFailureOnce = false;
            throw new Error('Network Timeout: Remote API Gateway was temporarily unreachable.');
        }
    }
}

// ============================================================================
// 4. BASE DECORATOR CLASS (Structural Linkage Layer)
// ============================================================================

export abstract class PaymentGatewayDecorator implements IPaymentGateway {
    constructor(protected readonly wrapper: IPaymentGateway) { }

    public createOrder(amount: number, currency: string, meta: OrderMetadata): Promise<PaymentOrder> {
        return this.wrapper.createOrder(amount, currency, meta);
    }
    public capturePayment(orderId: string, paymentId: string): Promise<PaymentCapture> {
        return this.wrapper.capturePayment(orderId, paymentId);
    }
    public refund(paymentId: string, amount: number, reason: string): Promise<RefundResult> {
        return this.wrapper.refund(paymentId, amount, reason);
    }
    public getPaymentStatus(paymentId: string): Promise<PaymentStatus> {
        return this.wrapper.getPaymentStatus(paymentId);
    }
}

// ============================================================================
// 5. CONCRETE DECORATORS (Cross-Cutting Concern Layers)
// ============================================================================

export class LoggingPaymentGateway extends PaymentGatewayDecorator {
    constructor(wrapped: IPaymentGateway, private readonly logger: Logger) {
        super(wrapped);
    }

    override async createOrder(amount: number, currency: string, meta: OrderMetadata): Promise<PaymentOrder> {
        const start = Date.now();
        try {
            const order = await this.wrapper.createOrder(amount, currency, meta);
            this.logger.log(`createOrder successful: ${order.id} for amount ${amount} ${currency}`);
            return order;
        } catch (error: any) {
            this.logger.error(`createOrder failed: ${error.message || error}`);
            throw error;
        } finally {
            const duration = Date.now() - start;
            this.logger.log(`createOrder took ${duration}ms`);
        }
    }

    override async capturePayment(orderId: string, paymentId: string): Promise<PaymentCapture> {
        const start = Date.now();
        try {
            const capture = await this.wrapper.capturePayment(orderId, paymentId);
            this.logger.log(`capturePayment successful: ${capture.id} for order ${orderId}`);
            return capture;
        } catch (error: any) {
            this.logger.error(`capturePayment failed: ${error.message || error}`);
            throw error;
        } finally {
            const duration = Date.now() - start;
            this.logger.log(`capturePayment took ${duration}ms`);
        }
    }

    override async refund(paymentId: string, amount: number, reason: string): Promise<RefundResult> {
        const start = Date.now();
        try {
            const refund = await this.wrapper.refund(paymentId, amount, reason);
            this.logger.log(`refund successful: ${refund.id} for payment ${paymentId}`);
            return refund;
        } catch (error: any) {
            this.logger.error(`refund failed: ${error.message || error}`);
            throw error;
        } finally {
            const duration = Date.now() - start;
            this.logger.log(`refund took ${duration}ms`);
        }
    }

    override async getPaymentStatus(paymentId: string): Promise<PaymentStatus> {
        const start = Date.now();
        try {
            const status = await this.wrapper.getPaymentStatus(paymentId);
            this.logger.log(`getPaymentStatus successful for payment ${paymentId}: ${status.status}`);
            return status;
        } catch (error: any) {
            this.logger.error(`getPaymentStatus failed: ${error.message || error}`);
            throw error;
        } finally {
            const duration = Date.now() - start;
            this.logger.log(`getPaymentStatus took ${duration}ms`);
        }
    }
}

export class RetryPaymentGateway extends PaymentGatewayDecorator {
    constructor(
        wrapped: IPaymentGateway, 
        private readonly maxRetries: number = 3, 
        private readonly backoffMs: number = 1000
    ) {
        super(wrapped);
    }

    private async retry<T>(fn: () => Promise<T>, operationName: string, attempt: number = 1): Promise<T> {
        while (attempt <= this.maxRetries) {
            try {
                return await fn();
            } catch (error) {
                if (attempt === this.maxRetries) {
                    throw error;
                }
                const delay = this.backoffMs * Math.pow(2, attempt - 1);
                console.warn(`[Retry Engine] Transient failure encountered on ${operationName}. Attempt ${attempt}/${this.maxRetries} failed. Backing off for ${delay}ms before re-execution...`);
                await new Promise(resolve => setTimeout(resolve, delay));
                attempt++;
            }
        }
        throw new Error(`Max execution retries exceeded on operation: ${operationName}`);
    }

    override async createOrder(amount: number, currency: string, meta: OrderMetadata): Promise<PaymentOrder> {
        return this.retry(() => this.wrapper.createOrder(amount, currency, meta), 'createOrder');
    }

    override async capturePayment(orderId: string, paymentId: string): Promise<PaymentCapture> {
        return this.retry(() => this.wrapper.capturePayment(orderId, paymentId), 'capturePayment');
    }

    override async refund(paymentId: string, amount: number, reason: string): Promise<RefundResult> {
        return this.retry(() => this.wrapper.refund(paymentId, amount, reason), 'refund');
    }

    override async getPaymentStatus(paymentId: string): Promise<PaymentStatus> {
        return this.retry(() => this.wrapper.getPaymentStatus(paymentId), 'getPaymentStatus');
    }
}

// ============================================================================
// 6. PIPELINE COMPOSITION ROOT & RUNTIME DEMONSTRATION
// ============================================================================

async function runDecoratorPipelineDemo() {
    console.log("=== Initializing Onion-Architecture Decorator Pipeline ===");

    const baseGateway = new BasePaymentGateway();
    const loggerInstance = new ConsoleLogger();

    /**
     * Composition Stack:
     * Request -> [Retry Layer] -> [Logging Layer] -> [Core Gateway Logic]
     */
    const secureGatewayPipeline: IPaymentGateway = new RetryPaymentGateway(
        new LoggingPaymentGateway(baseGateway, loggerInstance),
        3,
        500
    );

    const mockOrderPayload: OrderMetadata = {
        customerId: "cust_99411",
        items: [
            { productId: "prod_architecture_book", quantity: 1, price: 49.99 },
            { productId: "prod_cloud_license", quantity: 2, price: 150.00 }
        ]
    };

    console.log("\n--- Scenario A: Normal Happy-Path Execution Flow ---");
    const stableOrder = await secureGatewayPipeline.createOrder(349.99, "USD", mockOrderPayload);
    console.log(`Execution Returned Order ID -> ${stableOrder.id}`);

    console.log("\n--- Scenario B: Simulating Transient Fault Recovery Flow ---");
    baseGateway.enableTransientFailureSimulation(); // Injects a single failure down inside the base component
    
    // The retry decorator wraps the logger. The logger will error out on attempt 1, 
    // retry kicks in, backs off, and runs attempt 2 which catches the successful outcome.
    const resilientOrder = await secureGatewayPipeline.createOrder(349.99, "USD", mockOrderPayload);
    console.log(`Execution Resumed and Returned Order ID -> ${resilientOrder.id}`);

    console.log("\n=== Structural Verification Complete ===");
}

// Execute the bootstrap function if executing inside a direct node/ts runtime context
runDecoratorPipelineDemo();