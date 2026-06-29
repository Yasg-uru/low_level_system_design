interface AppEvent {
    type: string;
    payload: any; // Using any/unknown depending on strictness, structured below
    timestamp: Date;
}

interface CartItem {
    id: string;
    quantity: number;
}

type EventHandler = (event: AppEvent) => Promise<void>;

interface IEventBus {
    subscribe(eventType: string, handler: EventHandler): void;
    publish(event: AppEvent): Promise<void>;
}

class EventBus implements IEventBus {
    private handlers = new Map<string, EventHandler[]>();

    subscribe(eventType: string, handler: EventHandler): void {
        if (!this.handlers.has(eventType)) {
            this.handlers.set(eventType, []);
        }
        this.handlers.get(eventType)?.push(handler);
    }

    async publish(event: AppEvent): Promise<void> {
        const handlers: EventHandler[] = this.handlers.get(event.type) ?? [];
        
        // Executes all handlers concurrently and catches rejections safely
        const results = await Promise.allSettled(handlers.map(h => h(event)));
        
        results.forEach((result, index) => {
            if (result.status === "rejected") {
                console.error(`Handler [${index}] failed for ${event.type}:`, result.reason);
            }
        });
    }
}

class OrderService {
    // lowercase property name for idiomatic TypeScript
    constructor(private eventBus: IEventBus) { }

    async placeOrder(userId: string, cartItems: CartItem[]): Promise<void> {
        // Business logic execution...
        console.log(`Processing order for user: ${userId}`);

        // Generate a random 6-digit ID safely
        const orderId = Math.floor(Math.random() * 900000) + 100000;

        await this.eventBus.publish({
            type: "ORDER_PLACED",
            payload: { 
                orderId, 
                userId, 
                items: cartItems 
            },
            timestamp: new Date()
        });
    }
}

class InventoryService {
    constructor(private eventBus: IEventBus) {
        this.eventBus.subscribe("ORDER_PLACED", this.onOrderPlaced.bind(this));
        this.eventBus.subscribe("ORDER_CANCELLED", this.onOrderCancelled.bind(this));
    }

    // Handlers now properly accept the event argument matching EventHandler type
    async onOrderPlaced(event: AppEvent): Promise<void> {
        console.log("Decrementing inventory for items:", event.payload.items);
    }

    async onOrderCancelled(event: AppEvent): Promise<void> {
        console.log("Incrementing inventory for order:", event.payload.orderId);
    }
}

class NotificationService {
    constructor(private eventBus: IEventBus) {
        this.eventBus.subscribe("ORDER_PLACED", this.onOrderPlaceNotify.bind(this));
    }

    async onOrderPlaceNotify(event: AppEvent): Promise<void> {
        console.log(`Notification sent to user ${event.payload.userId} for Order #${event.payload.orderId}`);
    }
}

// --- Execution ---
const eventBus = new EventBus();
const orderService = new OrderService(eventBus);
const inventoryService = new InventoryService(eventBus);
const notificationService = new NotificationService(eventBus);

// Dummy cart data matching the CartItem[] type requirement
const mockCart: CartItem[] = [
    { id: "prod-101", quantity: 2 },
    { id: "prod-202", quantity: 1 }
];

// Execute
orderService.placeOrder("user_9876", mockCart);