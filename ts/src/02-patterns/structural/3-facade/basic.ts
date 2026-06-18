/**
 * ============================================================================
 * FACADE DESIGN PATTERN: BACKEND ECOSYSTEM ORCHESTRATION
 * ============================================================================
 * * Purpose: Consolidates complex, interdependent micro-services into explicit,
 * unified macro-operations. Prevents business logic leakage into controllers.
 * * Structural Layout:
 * - Facade:           CheckoutFacade (Orchestrates database, payments, inventory, audits)
 * - Clients:          CheckoutController, MobileCheckoutController, AdminOrderController, CheckoutRetryJob
 * - Subsystems:       OrderService, PaymentService, InventoryService, NotificationService, AuditService
 */

// ============================================================================
// 1. EXTENDED TYPES, SCHEMAS & MOCK STRUCTS
// ============================================================================

export interface CartItem {
    productId: string;
    quantity: number;
    price: number;
}

export interface Order {
    id: string;
    userId: string;
    items: CartItem[];
    total: number;
    paymentId: string;
    status: "confirmed" | "cancelled";
    createdAt: Date;
}

export interface CreateOrderDTO {
    userId: string;
    items: CartItem[];
    total: number;
    paymentId: string;
}

export interface PaymentResult {
    success: boolean;
    transactionId: string;
    amount: number;
}

export interface CheckoutRequest {
    userId: string;
    items: CartItem[];
}

export interface CheckoutResult {
    success: boolean;
    order?: Order;
    error?: string;
}

// Minimal placeholder interfaces for Express-like standard router environments
export interface Request { user?: any; body: any; params: any; }
export interface Response { status(code: number): Response; json(data: any): void; }

// External abstract contracts
export interface IOrderRepository { save(d: any): Promise<Order>; findById(id: string): Promise<Order | null>; updateStatus(id: string, s: string): Promise<void>; }
export interface IPaymentGateway { createOrder(a: number, c: string, m: any): Promise<any>; capturePayment(o: string, p: string): Promise<any>; refund(p: string, a: number, r: string): Promise<any>; }
export interface IInventoryRepository { getStock(id: string): Promise<number>; decrementStock(id: string, q: number): Promise<void>; incrementStock(id: string, q: number): Promise<void>; }
export interface INotificationSender { send(to: string, sub: string, body: string): Promise<void>; }
export interface IAuditRepository { save(log: any): Promise<void>; }
export interface IUserRepository { findById(id: string): Promise<{ id: string; email: string } | null>; }

// ============================================================================
// 2. DEPENDENT CORE BUSINESS DOMAIN SERVICES
// ============================================================================

export class OrderService {
    constructor(private readonly repo: IOrderRepository) {}

    public async create(data: CreateOrderDTO): Promise<Order> {
        return this.repo.save({ ...data, status: "confirmed", createdAt: new Date() });
    }

    public async cancel(orderId: string): Promise<void> {
        await this.repo.updateStatus(orderId, "cancelled");
    }

    public async findById(orderId: string): Promise<Order | null> {
        return this.repo.findById(orderId);
    }
}

export class PaymentService {
    constructor(private readonly gateway: IPaymentGateway) {}

    public async charge(amount: number, userId: string): Promise<PaymentResult> {
        const order = await this.gateway.createOrder(amount, "INR", {
            userId, description: "Order checkout", receiptId: `rcpt_${Date.now()}`
        });
        const capture = await this.gateway.capturePayment(order.id, order.id);
        return { success: capture.status === 'captured' || capture.success, transactionId: capture.id || capture.transactionId, amount };
    }

    public async refund(transactionId: string, amount: number, reason: string): Promise<void> {
        await this.gateway.refund(transactionId, amount, reason);
    }
}

export class InventoryService {
    constructor(private readonly repo: IInventoryRepository) {}

    public async reserveItems(items: CartItem[]): Promise<{ success: boolean; reason?: string }> {
        for (const item of items) {
            const stock = await this.repo.getStock(item.productId);
            if (stock < item.quantity) {
                return { success: false, reason: `${item.productId} out of stock` };
            }
        }
        await Promise.all(items.map(i => this.repo.decrementStock(i.productId, i.quantity)));
        return { success: true };
    }

    public async releaseItems(items: CartItem[]): Promise<void> {
        await Promise.all(items.map(i => this.repo.incrementStock(i.productId, i.quantity)));
    }
}

export class NotificationService {
    constructor(private readonly sender: INotificationSender) {}

    public async sendOrderConfirmation(email: string, order: Order): Promise<void> {
        await this.sender.send(email, "Order Confirmed", `Order #${order.id} — ₹${order.total}`);
    }

    public async sendOrderCancelled(email: string, order: Order): Promise<void> {
        await this.sender.send(email, "Order Cancelled", `Order #${order.id} has been cancelled`);
    }

    public async sendRefundProcessed(email: string, order: Order, amount: number): Promise<void> {
        await this.sender.send(email, "Refund Processed", `₹${amount} refunded for order #${order.id}`);
    }
}

export class AuditService {
    constructor(private readonly repo: IAuditRepository) {}

    public async log(action: string, metadata: Record<string, unknown>): Promise<void> {
        await this.repo.save({ action, metadata, timestamp: new Date() });
    }
}

// ============================================================================
// 3. THE GRAND CORE FACADE
// ============================================================================

export class CheckoutFacade {
    constructor(
        private readonly orderService:        OrderService,
        private readonly paymentService:      PaymentService,
        private readonly inventoryService:    InventoryService,
        private readonly notificationService: NotificationService,
        private readonly auditService:        AuditService,
        private readonly userRepo:            IUserRepository
    ) {}

    /**
     * Singular atomic workflow orchestrating validation, reservation, 
     * charging with auto-rollback, and event auditing.
     */
    public async checkout(request: CheckoutRequest): Promise<CheckoutResult> {
        const { userId, items } = request;
        const total = items.reduce((s, i) => s + i.price * i.quantity, 0);
        const user  = await this.userRepo.findById(userId);

        if (!user) {
            return { success: false, error: "User not found" };
        }

        // Step 1: Reserve inventory
        const reservation = await this.inventoryService.reserveItems(items);
        if (!reservation.success) {
            return { success: false, error: reservation.reason };
        }

        // Step 2: Charge payment — rollback inventory on structural exception or denial
        let payment: PaymentResult;
        try {
            payment = await this.paymentService.charge(total, userId);
            if (!payment.success) throw new Error("Payment declined");
        } catch (err) {
            await this.inventoryService.releaseItems(items);
            await this.auditService.log("CHECKOUT_PAYMENT_FAILED", { userId, total, error: String(err) });
            return { success: false, error: "Payment failed" };
        }

        // Step 3: Instantiate immutable order records
        const order = await this.orderService.create({
            userId, items, total, paymentId: payment.transactionId
        });

        // Step 4: Fire-and-forget notifications (Prevents pipeline blockages if mail systems hang)
        this.notificationService
            .sendOrderConfirmation(user.email, order)
            .catch(err => console.error(`[Async Error Fallback] Notification failed for user ${user.id}:`, err));

        // Step 5: Master audit ledger logging entries
        await this.auditService.log("ORDER_PLACED", {
            orderId: order.id, userId, total, paymentId: payment.transactionId
        });

        return { success: true, order };
    }

    /**
     * Reverses atomic state alterations across payments, inventory pools, and statuses.
     */
    public async cancelOrder(orderId: string): Promise<CheckoutResult> {
        const order = await this.orderService.findById(orderId);
        if (!order) return { success: false, error: "Order not found" };

        const user = await this.userRepo.findById(order.userId);
        const email = user ? user.email : "unknown@domain.internal";

        // Refund payment settlement layer
        await this.paymentService.refund(order.paymentId, order.total, "Order cancelled");

        // Deallocate resource reservation locks
        await this.inventoryService.releaseItems(order.items);

        // Mutate storage lifecycle flags
        await this.orderService.cancel(orderId);

        // Broadcast notifications
        await this.notificationService.sendOrderCancelled(email, order);
        await this.notificationService.sendRefundProcessed(email, order, order.total);

        // Audit trace mapping
        await this.auditService.log("ORDER_CANCELLED", { orderId, userId: order.userId });

        return { success: true, order: { ...order, status: "cancelled" } };
    }
}

// ============================================================================
// 4. MULTI-CLIENT CONTROLLERS & WORKERS (De-duplicated Clean Architecture)
// ============================================================================

export class CheckoutController {
    constructor(private readonly facade: CheckoutFacade) {}

    public async handleCheckout(req: Request, res: Response): Promise<void> {
        const result = await this.facade.checkout({
            userId: req.user.id,
            items:  req.body.items
        });

        if (!result.success) {
            return res.status(400).json({ error: result.error });
        }
        res.json({ order: result.order });
    }

    public async handleCancel(req: Request, res: Response): Promise<void> {
        const result = await this.facade.cancelOrder(req.params.orderId);
        res.json(result);
    }
}

export class MobileCheckoutController {
    constructor(private readonly facade: CheckoutFacade) {}

    public async checkout(req: Request, res: Response): Promise<void> {
        const result = await this.facade.checkout({
            userId: req.user.id,
            items:  req.body.cart
        });
        res.json(result);
    }
}

export class AdminOrderController {
    constructor(private readonly facade: CheckoutFacade) {}

    public async placeOrderForCustomer(req: Request, res: Response): Promise<void> {
        const result = await this.facade.checkout({
            userId: req.body.customerId,
            items:  req.body.items
        });
        res.json(result);
    }
}

export class CheckoutRetryJob {
    constructor(private readonly facade: CheckoutFacade) {}

    public async run(queueDriver: { getAll(): Promise<any[]>; remove(id: string): Promise<void> }): Promise<void> {
        const pendingCheckouts = await queueDriver.getAll();

        for (const checkout of pendingCheckouts) {
            const result = await this.facade.checkout(checkout);
            if (result.success) {
                await queueDriver.remove(checkout.id);
            }
        }
    }
}