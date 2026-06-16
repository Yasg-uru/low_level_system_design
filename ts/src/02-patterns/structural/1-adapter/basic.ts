/**
 * ============================================================================
 * ADAPTER DESIGN PATTERN: PAYMENT GATEWAY WRAPPER
 * ============================================================================
 * * Purpose: Translates incompatible target vendor SDKs (Razorpay, Stripe) into
 * a clean, unified, and unified domain language interface matching your business logic.
 * * Structural Layout:
 * - Target Interface: IPaymentGateway (Your system's abstract standard)
 * - Adapters:         RazorpayAdapter, StripeAdapter (Translates Rupees -> Paise/Cents)
 * - Adaptees:         RazorpayClient, StripeClient (Third-party SDK packages)
 * - Client:           OrderService (Consumes Target interface using Dependency Injection)
 */

// ============================================================================
// 1. THIRD-PARTY CLIENT STUBS (Adaptee Mock Interfaces)
// ============================================================================

export class RazorpayClient {
  constructor(config: { key_id: string; key_secret: string }) {}
  public orders = {
    create: async (data: any): Promise<RazorpayOrderResponse> => ({
      id: "rzp_order_99211",
      entity: "order",
      amount: data.amount,
      amount_paid: 0,
      amount_due: data.amount,
      currency: data.currency,
      receipt: data.receipt,
      status: "created",
      created_at: Math.floor(Date.now() / 1000),
    }),
  };
  public payments = {
    fetch: async (id: string): Promise<RazorpayPaymentResponse> => ({
      id,
      amount: 500000, // 5000 INR in paise
      currency: "INR",
      status: "authorized",
      order_id: "rzp_order_99211",
      captured: false,
      created_at: Math.floor(Date.now() / 1000),
    }),
    capture: async (id: string, amount: number): Promise<any> => ({ status: "captured" }),
    refund: async (id: string, options: any): Promise<any> => ({
      id: "rfnd_001",
      amount: options.amount,
      status: "processed",
    }),
  };
}

export class StripeClient {
  constructor(secretKey: string) {}
  public paymentIntents = {
    create: async (data: any): Promise<StripePaymentIntentResponse> => ({
      id: "pi_stripe_11204",
      amount: data.amount,
      currency: data.currency,
      status: "requires_payment_method",
      client_secret: "secret_sec_9912",
      created: Math.floor(Date.now() / 1000),
      charges: { data: [] },
    }),
    confirm: async (id: string): Promise<StripePaymentIntentResponse> => ({
      id,
      amount: 500000,
      currency: "inr",
      status: "succeeded",
      client_secret: "secret_sec_9912",
      created: Math.floor(Date.now() / 1000),
      charges: { data: [{ id: "ch_001", captured: true }] },
    }),
    retrieve: async (id: string): Promise<StripePaymentIntentResponse> => ({
      id,
      amount: 500000,
      currency: "inr",
      status: "succeeded",
      client_secret: "secret_sec_9912",
      created: Math.floor(Date.now() / 1000),
      charges: { data: [{ id: "ch_001", captured: true }] },
    }),
  };
  public refunds = {
    create: async (data: any): Promise<any> => ({
      id: "re_stripe_002",
      amount: data.amount,
      status: "succeeded",
    }),
  };
}

// ============================================================================
// 2. TARGET DOMAIN INTERFACES & REPO UTILITIES
// ============================================================================

export interface IPaymentGateway {
  createOrder(amount: number, currency: string, metadata: OrderMetadata): Promise<PaymentOrder>;
  capturePayment(orderId: string, paymentId: string): Promise<PaymentCapture>;
  refund(paymentId: string, amount: number, reason: string): Promise<RefundResult>;
  getPaymentStatus(paymentId: string): Promise<PaymentStatus>;
}

export interface OrderMetadata {
  userId: string;
  description: string;
  receiptId: string;
}

export interface PaymentOrder {
  id: string;
  amount: number;
  currency: string;
  status: "created" | "attempted" | "paid";
  createdAt: Date;
}

export interface PaymentCapture {
  success: boolean;
  transactionId: string;
  capturedAt: Date;
}

export interface RefundResult {
  refundId: string;
  amount: number;
  status: "pending" | "processed" | "failed";
}

export type PaymentStatus = "created" | "authorized" | "captured" | "refunded" | "failed";

export interface CartItem {
  id: string;
  name: string;
  price: number;
  quantity: number;
}

export interface Order {
  id: string;
  userId: string;
  items: CartItem[];
  total: number;
  paymentOrderId: string;
  status: "pending" | "confirmed" | "cancelled";
}

export interface IOrderRepository {
  save(order: Omit<Order, "id">): Promise<Order>;
  findById(id: string): Promise<Order | undefined>;
  updateStatus(id: string, status: Order["status"]): Promise<void>;
}

export interface INotificationService {
  sendOrderConfirmation(userId: string, order: Order): Promise<void>;
  sendOrderCancelled(userId: string, order: Order): Promise<void>;
}

// ============================================================================
// 3. VENDOR API TYPING SCHEMAS
// ============================================================================

export interface RazorpayOrderResponse {
  id: string;
  entity: string;
  amount: number; // In Paise
  amount_paid: number;
  amount_due: number;
  currency: string;
  receipt: string;
  status: string;
  created_at: number; // Unix Epoch Timestamp
}

export interface RazorpayPaymentResponse {
  id: string;
  amount: number; // In Paise
  currency: string;
  status: string;
  order_id: string;
  captured: boolean;
  created_at: number;
}

export interface StripePaymentIntentResponse {
  id: string;
  amount: number; // Smallest Unit (Cents/Paise)
  currency: string;
  status: string;
  client_secret: string;
  created: number;
  charges: { data: Array<{ id: string; captured: boolean }> };
}

// ============================================================================
// 4. CONCRETE ADAPTERS (Translates Vendor Quirks -> Domain Norms)
// ============================================================================

export class RazorpayAdapter implements IPaymentGateway {
  private readonly client: RazorpayClient;

  constructor(keyId: string, keySecret: string) {
    this.client = new RazorpayClient({ key_id: keyId, key_secret: keySecret });
  }

  public async createOrder(amount: number, currency: string, metadata: OrderMetadata): Promise<PaymentOrder> {
    const amountInPaise = Math.round(amount * 100);

    const razorpayOrder: RazorpayOrderResponse = await this.client.orders.create({
      amount: amountInPaise,
      currency: currency.toUpperCase(),
      receipt: metadata.receiptId,
      notes: {
        userId: metadata.userId,
        description: metadata.description,
      },
    });

    return {
      id: razorpayOrder.id,
      amount: razorpayOrder.amount / 100, // Paise -> Rupees
      currency: razorpayOrder.currency,
      status: this.mapOrderStatus(razorpayOrder.status),
      createdAt: new Date(razorpayOrder.created_at * 1000),
    };
  }

  public async capturePayment(orderId: string, paymentId: string): Promise<PaymentCapture> {
    const payment: RazorpayPaymentResponse = await this.client.payments.fetch(paymentId);
    await this.client.payments.capture(paymentId, payment.amount);

    return {
      success: true,
      transactionId: paymentId,
      capturedAt: new Date(),
    };
  }

  public async refund(paymentId: string, amount: number, reason: string): Promise<RefundResult> {
    const refund = await this.client.payments.refund(paymentId, {
      amount: Math.round(amount * 100),
      notes: { reason },
    });

    return {
      refundId: refund.id,
      amount: refund.amount / 100,
      status: refund.status === "processed" ? "processed" : "pending",
    };
  }

  public async getPaymentStatus(paymentId: string): Promise<PaymentStatus> {
    const payment = await this.client.payments.fetch(paymentId);
    return this.mapPaymentStatus(payment.status);
  }

  private mapOrderStatus(status: string): PaymentOrder["status"] {
    const map: Record<string, PaymentOrder["status"]> = {
      created: "created",
      attempted: "attempted",
      paid: "paid",
    };
    return map[status] ?? "created";
  }

  private mapPaymentStatus(status: string): PaymentStatus {
    const map: Record<string, PaymentStatus> = {
      created: "created",
      authorized: "authorized",
      captured: "captured",
      refunded: "refunded",
      failed: "failed",
    };
    return map[status] ?? "failed";
  }
}

export class StripeAdapter implements IPaymentGateway {
  private readonly client: StripeClient;

  constructor(secretKey: string) {
    this.client = new StripeClient(secretKey);
  }

  public async createOrder(amount: number, currency: string, metadata: OrderMetadata): Promise<PaymentOrder> {
    const amountInSmallestUnit = Math.round(amount * 100);

    const intent: StripePaymentIntentResponse = await this.client.paymentIntents.create({
      amount: amountInSmallestUnit,
      currency: currency.toLowerCase(),
      metadata: {
        userId: metadata.userId,
        description: metadata.description,
        receipt_id: metadata.receiptId,
      },
      automatic_payment_methods: { enabled: true },
    });

    return {
      id: intent.id,
      amount: intent.amount / 100,
      currency: intent.currency.toUpperCase(),
      status: "created",
      createdAt: new Date(intent.created * 1000),
    };
  }

  public async capturePayment(orderId: string, paymentId: string): Promise<PaymentCapture> {
    const intent = await this.client.paymentIntents.confirm(orderId);

    return {
      success: intent.status === "succeeded",
      transactionId: intent.charges.data[0]?.id ?? orderId,
      capturedAt: new Date(),
    };
  }

  public async refund(paymentId: string, amount: number, reason: string): Promise<RefundResult> {
    const refund = await this.client.refunds.create({
      charge: paymentId,
      amount: Math.round(amount * 100),
      reason: "requested_by_customer",
    });

    return {
      refundId: refund.id,
      amount: (refund.amount ?? 0) / 100,
      status: refund.status === "succeeded" ? "processed" : "pending",
    };
  }

  public async getPaymentStatus(paymentId: string): Promise<PaymentStatus> {
    const intent = await this.client.paymentIntents.retrieve(paymentId);
    const map: Record<string, PaymentStatus> = {
      requires_payment_method: "created",
      requires_confirmation: "authorized",
      succeeded: "captured",
      canceled: "failed",
    };
    return map[intent.status] ?? "failed";
  }
}

// ============================================================================
// 5. CLIENT APPLICATION SERVICE (Speaks Pure Core Domain Language)
// ============================================================================

export class OrderService {
  constructor(
    private readonly orderRepo: IOrderRepository,
    private readonly gateway: IPaymentGateway, // Injected decoupling abstraction
    private readonly notifier: INotificationService
  ) {}

  public async checkout(userId: string, items: CartItem[]): Promise<{ order: Order; paymentOrder: PaymentOrder }> {
    const total = items.reduce((sum, item) => sum + item.price * item.quantity, 0);

    const paymentOrder = await this.gateway.createOrder(total, "INR", {
      userId,
      description: `Order context bundle contains ${items.length} unique lines.`,
      receiptId: `rcpt_${userId}_${Date.now()}`,
    });

    const order = await this.orderRepo.save({
      userId,
      items,
      total,
      paymentOrderId: paymentOrder.id,
      status: "pending",
    });

    return { order, paymentOrder };
  }

  public async confirmPayment(orderId: string, paymentId: string): Promise<Order> {
    const order = await this.orderRepo.findById(orderId);
    if (!order) throw new Error(`Target record not found for Order tracking ID: ${orderId}`);

    const capture = await this.gateway.capturePayment(order.paymentOrderId, paymentId);
    if (!capture.success) throw new Error("Crucial core transaction capturing sequence rejected by vendor fallback.");

    await this.orderRepo.updateStatus(orderId, "confirmed");
    await this.notifier.sendOrderConfirmation(order.userId, order);

    return (await this.orderRepo.findById(orderId))!;
  }

  public async cancelOrder(orderId: string, paymentId: string): Promise<void> {
    const order = await this.orderRepo.findById(orderId);
    if (!order) throw new Error(`Target record not found for Order tracking ID: ${orderId}`);

    await this.gateway.refund(paymentId, order.total, "Order cancelled by user");
    await this.orderRepo.updateStatus(orderId, "cancelled");
    await this.notifier.sendOrderCancelled(order.userId, order);
  }
}

// ============================================================================
// 6. PRODUCTION CONCRETE INFRASTRUCTURE MOCK SEEDING
// ============================================================================

export class PostgresOrderRepository implements IOrderRepository {
  private databaseStore = new Map<string, Order>();

  public async save(order: Omit<Order, "id">): Promise<Order> {
    const recordId = `ord_db_${Math.floor(Math.random() * 9000 + 1000)}`;
    const newRecord: Order = { id: recordId, ...order };
    this.databaseStore.set(recordId, newRecord);
    return newRecord;
  }

  public async findById(id: string): Promise<Order | undefined> {
    return this.databaseStore.get(id);
  }

  public async updateStatus(id: string, status: Order["status"]): Promise<void> {
    const record = this.databaseStore.get(id);
    if (record) record.status = status;
  }
}

export class EmailNotificationService implements INotificationService {
  public async sendOrderConfirmation(userId: string, order: Order): Promise<void> {
    console.log(`[Notification Engine] Dispatched confirmation email out to client: <${userId}>`);
  }
  public async sendOrderCancelled(userId: string, order: Order): Promise<void> {
    console.log(`[Notification Engine] Dispatched cancellation receipts out to client: <${userId}>`);
  }
}

// ============================================================================
// 7. WIRING EXECUTION INFRASTRUCTURE
// ============================================================================

async function executePipelineDemo() {
  const repo = new PostgresOrderRepository();
  const logs = new EmailNotificationService();
  const sampleCart: CartItem[] = [{ id: "sku_772", name: "Full-Stack System Architecture Guide", price: 2499, quantity: 2 }];

  console.log("=== Initializing Application Integration Matrix ===");

  // Switch Adapters dynamically at the composition root
  const rzpAdapter = new RazorpayAdapter("rzp_live_key", "rzp_secret_hash");
  const checkoutEngine = new OrderService(repo, rzpAdapter, logs);

  const checkoutTxn = await checkoutEngine.checkout("usr_yash_choudhary", sampleCart);
  console.log(`\n[Core Application] Order Session Initialized -> App ID: ${checkoutTxn.order.id} | External Gateway Reference: ${checkoutTxn.paymentOrder.id}`);
  console.log(`                   Calculated Ledger Amount: ₹${checkoutTxn.paymentOrder.amount} ${checkoutTxn.paymentOrder.currency}`);

  await checkoutEngine.confirmPayment(checkoutTxn.order.id, "pay_capture_txn_88192");
  console.log("\n=== Integration Lifecycle Operations Concluded Successfully ===");
}

executePipelineDemo();