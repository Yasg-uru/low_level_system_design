package com.lld.patterns.structural.facade;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== FACADE DESIGN PATTERN DEMONSTRATION ===");

        // 1. Initialize Mock Repositories & Databases
        MockUserRepository userRepo = new MockUserRepository();
        MockInventoryRepository inventoryRepo = new MockInventoryRepository();
        MockOrderRepository orderRepo = new MockOrderRepository();
        MockAuditRepository auditRepo = new MockAuditRepository();

        // Seed initial data
        User yash = new User("usr_yash", "yash@domain.com");
        userRepo.addUser(yash);

        User customerB = new User("usr_cust_b", "customer.b@domain.com");
        userRepo.addUser(customerB);

        inventoryRepo.setStock("prod_design_patterns_book", 10);
        inventoryRepo.setStock("prod_refactoring_guide", 1);
        inventoryRepo.setStock("prod_out_of_stock_item", 0);

        // 2. Initialize Subsystem Clients & Gateways
        MockPaymentGateway paymentGateway = new MockPaymentGateway();
        MockNotificationSender notificationSender = new MockNotificationSender();

        // 3. Initialize Core Domain Services
        OrderService orderService = new OrderService(orderRepo);
        PaymentService paymentService = new PaymentService(paymentGateway);
        InventoryService inventoryService = new InventoryService(inventoryRepo);
        NotificationService notificationService = new NotificationService(notificationSender);
        AuditService auditService = new AuditService(auditRepo);

        // 4. Initialize the Grand Facade
        CheckoutFacade checkoutFacade = new CheckoutFacade(
            orderService,
            paymentService,
            inventoryService,
            notificationService,
            auditService,
            userRepo
        );

        // 5. Initialize Controller Clients
        CheckoutController webController = new CheckoutController(checkoutFacade);
        MobileCheckoutController mobileController = new MobileCheckoutController(checkoutFacade);
        AdminOrderController adminController = new AdminOrderController(checkoutFacade);
        CheckoutRetryJob retryJob = new CheckoutRetryJob(checkoutFacade);

        // ==========================================
        // SCENARIO 1: SUCCESSFUL WEB CHECKOUT
        // ==========================================
        System.out.println("\n--- SCENARIO 1: Successful Web Checkout ---");
        List<CartItem> webCart = List.of(
            new CartItem("prod_design_patterns_book", 2, 1200.0), // 2400.0
            new CartItem("prod_refactoring_guide", 1, 999.0)      // 999.0 -> Total: 3399.0
        );
        Map<String, Object> webBody = Map.of("items", webCart);
        Request webReq = new Request(yash, webBody, Collections.emptyMap());
        Response webRes = new Response();

        webController.handleCheckout(webReq, webRes);
        System.out.println("HTTP Response Status: " + webRes.getStatus());
        System.out.println("HTTP Response Body: " + webRes.getJsonPayload());

        // Save order ID for later cancellation test
        @SuppressWarnings("unchecked")
        Map<String, Object> responseData = (Map<String, Object>) webRes.getJsonPayload();
        Order createdOrder = (Order) responseData.get("order");
        String createdOrderId = createdOrder.getId();

        // Print stock levels
        System.out.println("Stock of prod_design_patterns_book: " + inventoryRepo.getStock("prod_design_patterns_book") + " (Expected: 8)");
        System.out.println("Stock of prod_refactoring_guide: " + inventoryRepo.getStock("prod_refactoring_guide") + " (Expected: 0)");

        // ==========================================
        // SCENARIO 2: OUT OF STOCK CHECKOUT
        // ==========================================
        System.out.println("\n--- SCENARIO 2: Out of Stock Checkout ---");
        List<CartItem> oosCart = List.of(
            new CartItem("prod_out_of_stock_item", 1, 500.0)
        );
        Map<String, Object> oosBody = Map.of("items", oosCart);
        Request oosReq = new Request(yash, oosBody, Collections.emptyMap());
        Response oosRes = new Response();

        webController.handleCheckout(oosReq, oosRes);
        System.out.println("HTTP Response Status: " + oosRes.getStatus());
        System.out.println("HTTP Response Body: " + oosRes.getJsonPayload());

        // ==========================================
        // SCENARIO 3: PAYMENT GATEWAY DECLINED (ROLLBACK INVENTORY)
        // ==========================================
        System.out.println("\n--- SCENARIO 3: Payment Declined (Inventory Rollback) ---");
        // Mock payment gateway to reject total > 5000
        List<CartItem> expensiveCart = List.of(
            new CartItem("prod_design_patterns_book", 5, 1200.0) // Total: 6000.0
        );
        Map<String, Object> expensiveBody = Map.of("items", expensiveCart);
        Request expensiveReq = new Request(yash, expensiveBody, Collections.emptyMap());
        Response expensiveRes = new Response();

        System.out.println("Stock of prod_design_patterns_book before request: " + inventoryRepo.getStock("prod_design_patterns_book"));
        webController.handleCheckout(expensiveReq, expensiveRes);
        System.out.println("HTTP Response Status: " + expensiveRes.getStatus());
        System.out.println("HTTP Response Body: " + expensiveRes.getJsonPayload());
        System.out.println("Stock of prod_design_patterns_book after rollback: " + inventoryRepo.getStock("prod_design_patterns_book") + " (Expected: 8)");

        // ==========================================
        // SCENARIO 4: CANCEL ORDER (REFUND AND RELEASE STOCK)
        // ==========================================
        System.out.println("\n--- SCENARIO 4: Cancel Order ---");
        Request cancelReq = new Request(yash, Collections.emptyMap(), Map.of("orderId", createdOrderId));
        Response cancelRes = new Response();

        System.out.println("Stock of prod_design_patterns_book before cancel: " + inventoryRepo.getStock("prod_design_patterns_book"));
        webController.handleCancel(cancelReq, cancelRes);
        System.out.println("HTTP Response Status: " + cancelRes.getStatus());
        System.out.println("Stock of prod_design_patterns_book after cancel: " + inventoryRepo.getStock("prod_design_patterns_book") + " (Expected: 10)");

        // ==========================================
        // SCENARIO 5: ADMIN ORDER PLACE & QUEUE RETRY JOB
        // ==========================================
        System.out.println("\n--- SCENARIO 5: Admin Order Placement & Queue Retry ---");
        
        // Setup a mock Queue
        List<CheckoutRetryJob.QueueItem> retryQueue = new ArrayList<>();
        List<CartItem> retryCart = List.of(new CartItem("prod_design_patterns_book", 1, 1200.0));
        retryQueue.add(new CheckoutRetryJob.QueueItem("job_992", "usr_cust_b", retryCart));

        CheckoutRetryJob.QueueDriver queueDriver = new CheckoutRetryJob.QueueDriver() {
            @Override
            public List<CheckoutRetryJob.QueueItem> getAll() {
                return new ArrayList<>(retryQueue);
            }

            @Override
            public void remove(String id) {
                retryQueue.removeIf(item -> item.getId().equals(id));
                System.out.println("[Queue Driver] Removed job from queue: " + id);
            }
        };

        System.out.println("Queue items count before retry job: " + retryQueue.size());
        retryJob.run(queueDriver);
        System.out.println("Queue items count after successful retry job: " + retryQueue.size() + " (Expected: 0)");

        // Small wait to allow async threads to output logs
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    // ========================================================================
    // MOCK IMPLEMENTATIONS (STATIC INNER CLASSES)
    // ========================================================================

    static class MockUserRepository implements IUserRepository {
        private final Map<String, User> users = new HashMap<>();

        public void addUser(User user) {
            users.put(user.getId(), user);
        }

        @Override
        public User findById(String id) {
            return users.get(id);
        }
    }

    static class MockInventoryRepository implements IInventoryRepository {
        private final Map<String, Integer> stock = new HashMap<>();

        public void setStock(String productId, int qty) {
            stock.put(productId, qty);
        }

        @Override
        public int getStock(String productId) {
            return stock.getOrDefault(productId, 0);
        }

        @Override
        public void decrementStock(String productId, int quantity) {
            int current = getStock(productId);
            stock.put(productId, current - quantity);
            System.out.println("[Inventory DB] Reserved " + quantity + " units of " + productId);
        }

        @Override
        public void incrementStock(String productId, int quantity) {
            int current = getStock(productId);
            stock.put(productId, current + quantity);
            System.out.println("[Inventory DB] Released " + quantity + " units of " + productId);
        }
    }

    static class MockOrderRepository implements IOrderRepository {
        private final Map<String, Order> database = new HashMap<>();

        @Override
        public Order save(Order order) {
            database.put(order.getId(), order);
            System.out.println("[Order DB] Saved new order: " + order.getId() + " [Status: " + order.getStatus() + "]");
            return order;
        }

        @Override
        public Order findById(String id) {
            return database.get(id);
        }

        @Override
        public void updateStatus(String id, String status) {
            Order o = database.get(id);
            if (o != null) {
                Order updated = new Order(o.getId(), o.getUserId(), o.getItems(), o.getTotal(), o.getPaymentId(), status, o.getCreatedAt());
                database.put(id, updated);
                System.out.println("[Order DB] Updated order " + id + " status to: " + status);
            }
        }
    }

    static class MockAuditRepository implements IAuditRepository {
        @Override
        public void save(AuditLog log) {
            System.out.println("[Audit DB] " + log.getTimestamp() + " | Action: " + log.getAction() + " | Metadata: " + log.getMetadata());
        }
    }

    static class MockPaymentGateway implements IPaymentGateway {
        @Override
        public Map<String, Object> createOrder(double amount, String currency, Map<String, Object> metadata) throws Exception {
            if (amount > 5000.0) {
                throw new RuntimeException("Payment gateway threshold exceeded");
            }
            Map<String, Object> order = new HashMap<>();
            order.put("id", "pay_ord_" + UUID.randomUUID().toString().substring(0, 8));
            order.put("amount", amount);
            order.put("currency", currency);
            return order;
        }

        @Override
        public Map<String, Object> capturePayment(String orderId, String paymentId) throws Exception {
            Map<String, Object> capture = new HashMap<>();
            capture.put("id", "txn_" + UUID.randomUUID().toString().substring(0, 8));
            capture.put("status", "captured");
            capture.put("success", true);
            return capture;
        }

        @Override
        public void refund(String paymentId, double amount, String reason) throws Exception {
            System.out.println("[Payment Gateway] Refunded transaction " + paymentId + " amount: " + amount + " [Reason: " + reason + "]");
        }
    }

    static class MockNotificationSender implements INotificationSender {
        @Override
        public void send(String to, String subject, String body) throws Exception {
            System.out.println("[Notification Service] Email sent to: " + to + " | Subject: " + subject + " | Body: " + body);
        }
    }
}
