/**
 * ADVANCED COMMAND DESIGN PATTERN EXAMPLE
 * * Intent: Encapsulate requests as objects, allowing for parameterization, 
 * execution logging, batching, and strict transactional state rollback (Undo/Redo).
 */

// --- 1. SUPPORTING DOMAIN TYPES & INTERFACES ---

type OrderStatus = "pending" | "paid" | "shipped" | "cancelled";

interface CartItem {
  id: string;
  quantity: number;
}

interface Order {
  id: string;
  userId: string;
  items: CartItem[];
  total: number;
  status: OrderStatus;
}

interface AuditLogEntry {
  action: string;
  timestamp: Date;
  success: boolean;
  error?: string;
}

// --- 2. SERVICE MOCKS (STRICTLY TYPED) ---

class OrderService {
  private database = new Map<string, Order>();

  async create(data: { userId: string; items: CartItem[] }): Promise<Order> {
    const newOrder: Order = {
      id: `ord_${Math.random().toString(36).substr(2, 9)}`,
      userId: data.userId,
      items: data.items,
      total: data.items.length * 100.00, // Mock base calculation
      status: "pending"
    };
    this.database.set(newOrder.id, newOrder);
    return newOrder;
  }

  async findById(id: string): Promise<Order | undefined> {
    return this.database.get(id);
  }

  async updateStatus(id: string, status: OrderStatus): Promise<void> {
    const order = this.database.get(id);
    if (order) order.status = status;
  }

  async updateTotal(id: string, total: number): Promise<void> {
    const order = this.database.get(id);
    if (order) order.total = total;
  }

  async cancel(id: string): Promise<void> {
    await this.updateStatus(id, "cancelled");
  }
}

class EmailService {
  async sendCampaign(userIds: string[], campaignId: string): Promise<void> {
    console.log(`[EmailService] Dispatched campaign "${campaignId}" to ${userIds.length} targets.`);
  }
}

const AuditLogger = {
  async log(entry: AuditLogEntry): Promise<void> {
    console.log(`[AuditLog] [${entry.timestamp.toISOString()}] Success: ${entry.success} | Action: ${entry.action}${entry.error ? ` | Error: ${entry.error}` : ""}`);
  }
};

// --- 3. COMMAND CORE INTERFACES ---

interface CommandResult {
  success: boolean;
  data?: any; // Used explicitly for generic data payloads returned upon execution
  error?: string;
}

interface ICommand {
  execute(): Promise<CommandResult>;
  undo(): Promise<void>;
  getDescription(): string;  // For audit logs and dynamic tracking
  canUndo(): boolean;        // Some operations (like sending notifications) can't be recalled
}

// --- 4. CONCRETE COMMANDS ---

// Command 1: Place Order
class PlaceOrderCommand implements ICommand {
  private createdOrderId?: string;

  constructor(
    private orderService: OrderService,
    private userId: string,
    private items: CartItem[]
  ) {}

  async execute(): Promise<CommandResult> {
    try {
      const order = await this.orderService.create({
        userId: this.userId,
        items: this.items
      });
      this.createdOrderId = order.id; // Record historical parameter state for undo
      return { success: true, data: order };
    } catch (err) {
      return { success: false, error: (err as Error).message };
    }
  }

  async undo(): Promise<void> {
    if (!this.createdOrderId) throw new Error("Nothing to undo — execute() was never called");
    await this.orderService.cancel(this.createdOrderId);
  }

  canUndo(): boolean { return !!this.createdOrderId; }

  getDescription(): string {
    return `Place order for user ${this.userId} — ${this.items.length} items`;
  }
}

// Command 2: Apply Discount
class ApplyDiscountCommand implements ICommand {
  private previousTotal?: number;

  constructor(
    private orderService: OrderService,
    private orderId: string,
    private discountPercent: number
  ) {}

  async execute(): Promise<CommandResult> {
    const order = await this.orderService.findById(this.orderId);
    if (!order) return { success: false, error: "Order not found" };

    this.previousTotal = order.total; // Snapshot previous value state
    const discounted = order.total * (1 - this.discountPercent / 100);
    await this.orderService.updateTotal(this.orderId, discounted);

    return { success: true, data: { previous: this.previousTotal, new: discounted } };
  }

  async undo(): Promise<void> {
    if (this.previousTotal === undefined) throw new Error("Nothing to undo");
    await this.orderService.updateTotal(this.orderId, this.previousTotal);
  }

  canUndo(): boolean { return this.previousTotal !== undefined; }
  getDescription(): string { return `Apply ${this.discountPercent}% discount to order ${this.orderId}`; }
}

// Command 3: Bulk Status Update
class BulkUpdateOrderStatusCommand implements ICommand {
  private previousStatuses: Map<string, OrderStatus> = new Map();

  constructor(
    private orderService: OrderService,
    private orderIds: string[],
    private newStatus: OrderStatus
  ) {}

  async execute(): Promise<CommandResult> {
    for (const id of this.orderIds) {
      const order = await this.orderService.findById(id);
      if (order) this.previousStatuses.set(id, order.status);
    }

    await Promise.all(
      this.orderIds.map(id => this.orderService.updateStatus(id, this.newStatus))
    );

    return { success: true, data: { updated: this.orderIds.length } };
  }

  async undo(): Promise<void> {
    await Promise.all(
      [...this.previousStatuses.entries()].map(([id, status]) =>
        this.orderService.updateStatus(id, status)
      )
    );
  }

  canUndo(): boolean { return this.previousStatuses.size > 0; }
  getDescription(): string {
    return `Bulk update ${this.orderIds.length} orders to status "${this.newStatus}"`;
  }
}

// Command 4: Non-Undoable Operations
class SendMarketingEmailCommand implements ICommand {
  constructor(
    private emailService: EmailService,
    private userIds: string[],
    private campaignId: string
  ) {}

  async execute(): Promise<CommandResult> {
    await this.emailService.sendCampaign(this.userIds, this.campaignId);
    return { success: true, data: { sent: this.userIds.length } };
  }

  async undo(): Promise<void> {
    throw new Error("Cannot undo a sent email target operation");
  }

  canUndo(): boolean { return false; } // Explictly block invoker systems from trying
  getDescription(): string {
    return `Send campaign ${this.campaignId} to ${this.userIds.length} users`;
  }
}

// --- 5. THE INVOKER (ORCHESTRATOR) ---

class CommandInvoker {
  private history: ICommand[] = [];
  private redoStack: ICommand[] = [];
  private queue: ICommand[] = [];

  async execute(command: ICommand): Promise<CommandResult> {
    const result = await command.execute();

    if (result.success) {
      this.history.push(command);
      this.redoStack = []; // New deterministic structural mutation kills redo branches

      await AuditLogger.log({
        action: command.getDescription(),
        timestamp: new Date(),
        success: true
      });
    } else {
      await AuditLogger.log({
        action: command.getDescription(),
        timestamp: new Date(),
        success: false,
        error: result.error
      });
    }

    return result;
  }

  async undo(): Promise<void> {
    const command = this.history.pop();
    if (!command) throw new Error("Nothing to undo");
    if (!command.canUndo()) throw new Error(`${command.getDescription()} cannot be undone`);

    await command.undo();
    this.redoStack.push(command);

    await AuditLogger.log({
      action: `UNDO: ${command.getDescription()}`,
      timestamp: new Date(),
      success: true
    });
  }

  async redo(): Promise<void> {
    const command = this.redoStack.pop();
    if (!command) throw new Error("Nothing to redo");

    const result = await command.execute();
    if (result.success) this.history.push(command);
  }

  enqueue(command: ICommand): void {
    this.queue.push(command);
  }

  async flushQueue(): Promise<CommandResult[]> {
    const results: CommandResult[] = [];
    while (this.queue.length > 0) {
      const command = this.queue.shift()!;
      results.push(await this.execute(command));
    }
    return results;
  }

  getHistory(): string[] {
    return this.history.map(c => c.getDescription());
  }

  canUndo(): boolean {
    const last = this.history[this.history.length - 1];
    return !!last && last.canUndo();
  }
}

// --- 6. DEMONSTRATION RUN ---

async function runAdvancedCommandDemo(): Promise<void> {
  const orderService = new OrderService();
  const emailService = new EmailService();
  const invoker = new CommandInvoker();

  const cartItems: CartItem[] = [{ id: "item_prod_1", quantity: 3 }];

  console.log("--- 1. Immediate Execution Path ---");
  const placeResult = await invoker.execute(
    new PlaceOrderCommand(orderService, "user_1", cartItems)
  );
  
  const createdOrder = placeResult.data as Order;

  await invoker.execute(
    new ApplyDiscountCommand(orderService, createdOrder.id, 20)
  );

  console.log("\n--- 2. Reversion (Undo) Flow ---");
  if (invoker.canUndo()) {
    await invoker.undo(); // Reverts discount securely back to base scale
  }

  console.log("\n--- 3. Job Queuing Batch Processing ---");
  invoker.enqueue(new BulkUpdateOrderStatusCommand(orderService, [createdOrder.id], "shipped"));
  invoker.enqueue(new SendMarketingEmailCommand(emailService, ["user_1"], "diwali_sale"));

  // Flush the processing pool loops
  await invoker.flushQueue();

  console.log("\n--- 4. Full Internal History Audit Trails ---");
  console.log(invoker.getHistory());
}

runAdvancedCommandDemo();