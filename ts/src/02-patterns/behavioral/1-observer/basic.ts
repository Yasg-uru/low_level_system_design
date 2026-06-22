/**
 * OBSERVER DESIGN PATTERN EXAMPLE
 * * Intent: Define a one-to-many dependency between objects so that when one object 
 * changes state, all its dependents are notified and updated automatically.
 */

// --- 1. CORE DOMAIN INTERFACES ---

interface Order {
  id: string;
  items: string[];
  totalAmount: number;
}

interface OrderEvent {
  type: "ORDER_PLACED" | "ORDER_CANCELLED" | "ORDER_SHIPPED";
  order: Order;
  timestamp: Date;
}

// --- 2. OBSERVER & EMITTER INTERFACES ---

interface IOrderObserver {
  update(event: OrderEvent): Promise<void>;
}

interface IOrderEventEmitter {
  attach(observer: IOrderObserver): void;
  detach(observer: IOrderObserver): void;
  notify(event: OrderEvent): Promise<void>;
}

// --- 3. CONCRETE SUBJECT / EMITTER ---

class OrderEventEmitter implements IOrderEventEmitter {
  private observers: IOrderObserver[] = [];

  attach(observer: IOrderObserver): void {
    if (!this.observers.includes(observer)) {
      this.observers.push(observer);
    }
  }

  detach(observer: IOrderObserver): void {
    const index = this.observers.indexOf(observer);
    if (index > -1) {
      this.observers.splice(index, 1);
    }
  }

  async notify(event: OrderEvent): Promise<void> {
    // Executing updates sequentially (use Promise.all if you want parallel execution)
    for (const observer of this.observers) {
      await observer.update(event);
    }
  }
}

// --- 4. CONCRETE OBSERVERS ---

class NotificationService implements IOrderObserver {
  async update(event: OrderEvent): Promise<void> {
    console.log(`[NotificationService] Sending notification for event: ${event.type} at ${event.timestamp.toISOString()}`);
  }
}

class LoggingService implements IOrderObserver {
  async update(event: OrderEvent): Promise<void> {
    console.log(`[LoggingService] Logging event: ${event.type} for order ID: ${event.order.id} at ${event.timestamp.toISOString()}`);
  }
}

class AuditService implements IOrderObserver {
  async update(event: OrderEvent): Promise<void> {
    console.log(`[AuditService] Auditing event: ${event.type} for order ID: ${event.order.id} at ${event.timestamp.toISOString()}`);
  }
}

class LoyaltyPointAwarder implements IOrderObserver {
  async update(event: OrderEvent): Promise<void> {
    if (event.type === "ORDER_PLACED") {
      console.log(`[LoyaltyPointAwarder] Awarding loyalty points for order ID: ${event.order.id} at ${event.timestamp.toISOString()}`);
    }
  }
}

// --- 5. DEMONSTRATION RUN ---

async function runObserverDemo(): Promise<void> {
  const orderEventEmitter = new OrderEventEmitter();
  
  const notificationService = new NotificationService();
  const loggingService = new LoggingService();
  const auditService = new AuditService();
  const loyaltyPointAwarder = new LoyaltyPointAwarder();

  // Registering observers
  orderEventEmitter.attach(notificationService);
  orderEventEmitter.attach(loggingService);
  orderEventEmitter.attach(auditService);
  orderEventEmitter.attach(loyaltyPointAwarder);

  const sampleOrder: Order = { 
    id: "12345", 
    items: ["item1", "item2"], 
    totalAmount: 100 
  };

  console.log("--- Triggering Notification ---");
  await orderEventEmitter.notify({ 
    type: "ORDER_PLACED", 
    order: sampleOrder, 
    timestamp: new Date() 
  });
}

runObserverDemo();