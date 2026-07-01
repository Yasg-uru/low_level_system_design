/**
 * VISITOR DESIGN PATTERN EXAMPLE
 * * Intent: Represent an operation to be performed on the elements of an object structure. 
 * Visitor lets you define a new operation without changing the classes of the elements 
 * on which it operates.
 * * Key Benefit: Double dispatch mechanism decouples algorithms from the object structure.
 */

// --- 1. VISITOR INTERFACE ---
// Every new operational suite must implement this contract to handle each type of element.
interface OrderVisitor {
  visitPhysicalOrder(order: PhysicalOrder): void;
  visitDigitalOrder(order: DigitalOrder): void;
}

// --- 2. ELEMENT INTERFACE ---
// Defines the entrance point ('accept') for the visitor algorithms.
interface Order {
  accept(visitor: OrderVisitor): void;
}

// --- 3. CONCRETE ELEMENTS ---

class PhysicalOrder implements Order {
  constructor(
    public id: number,
    public amount: number,
    public shippingAddress: string
  ) {}

  public accept(visitor: OrderVisitor): void {
    // Double dispatch happens here: the element tells the visitor exactly what type it is
    visitor.visitPhysicalOrder(this);
  }
}

class DigitalOrder implements Order {
  constructor(
    public id: number,
    public amount: number,
    public email: string
  ) {}

  public accept(visitor: OrderVisitor): void {
    visitor.visitDigitalOrder(this);
  }
}

// --- 4. CONCRETE VISITORS ---

class TaxVisitor implements OrderVisitor {
  public visitPhysicalOrder(order: PhysicalOrder): void {
    const tax = order.amount * 0.18;
    console.log(`   [Tax Calculation] Physical Order #${order.id} Tax (18%) = ₹${tax.toFixed(2)}`);
  }

  public visitDigitalOrder(order: DigitalOrder): void {
    const tax = order.amount * 0.12;
    console.log(`   [Tax Calculation] Digital Order #${order.id} Tax (12%) = ₹${tax.toFixed(2)}`);
  }
}

class EmailVisitor implements OrderVisitor {
  public visitPhysicalOrder(order: PhysicalOrder): void {
    console.log(`   [Email Service] Shipping confirmation dispatched to address: ${order.shippingAddress}`);
  }

  public visitDigitalOrder(order: DigitalOrder): void {
    console.log(`   [Email Service] Secure item download links sent to: ${order.email}`);
  }
}

class AuditVisitor implements OrderVisitor {
  public visitPhysicalOrder(order: PhysicalOrder): void {
    console.log(`   [Audit Log] System scanned Physical Order record ID: ${order.id}`);
  }

  public visitDigitalOrder(order: DigitalOrder): void {
    console.log(`   [Audit Log] System scanned Digital Order record ID: ${order.id}`);
  }
}

// --- 5. DEMONSTRATION RUN ---

function runVisitorDemo(): void {
  const orders: Order[] = [
    new PhysicalOrder(1, 1500, "Delhi"),
    new DigitalOrder(2, 800, "john@example.com"),
  ];

  const visitors: OrderVisitor[] = [
    new TaxVisitor(),
    new EmailVisitor(),
    new AuditVisitor(),
  ];

  console.log("--- Initializing Dynamic Visitor Execution Passes ---");

  for (const visitor of visitors) {
    // Output the name of the strategy/visitor currently executing
    console.log(`\n▶ Running Visitor Operation: ${visitor.constructor.name}`);

    for (const order of orders) {
      // The exact method invocation is evaluated dynamically at runtime
      order.accept(visitor);
    }
  }
}

runVisitorDemo();