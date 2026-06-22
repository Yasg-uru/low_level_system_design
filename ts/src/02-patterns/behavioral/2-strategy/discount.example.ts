/**
 * ADVANCED STRATEGY DESIGN PATTERN EXAMPLE
 * * Intent: Encapsulate algorithms into separate, interchangeable classes.
 * * Advanced Twist: Showcases context-driven strategy runtime evaluations and 
 * the Null Object pattern (`NoDiscountStrategy`) for fallback safety.
 */

// --- 1. CORE DOMAIN INTERFACES ---

interface OrderItem {
  id: string;
  quantity: number;
}

interface Order {
  id: string;
  items: OrderItem[];
  total: number;
}

interface User {
  id: string;
  accountAgeYears: number;
}

interface DiscountContext {
  order: Order;
  user: User;
  currentDate: Date;
}

interface PricingResult {
  original: number;
  discount: number;
  final: number;
  strategyUsed: string;
}

// Interfaces specifically for Coupon Strategy
interface Coupon {
  type: "fixed" | "percentage";
  value: number;
}

interface ICouponService {
  isValid(code: string, userId: string): boolean;
  getCoupon(code: string): Coupon;
}

// --- 2. THE STRATEGY INTERFACE ---

interface IDiscountStrategy {
  getName(): string;
  isApplicable(ctx: DiscountContext): boolean; // Can this strategy apply?
  calculate(ctx: DiscountContext): number;     // Returns discount AMOUNT (not %)
}

// --- 3. CONCRETE STRATEGIES ---

// Strategy 1: Seasonal discount — flat % during sale periods
class SeasonalDiscountStrategy implements IDiscountStrategy {
  constructor(
    private percentage: number,
    private validFrom: Date,
    private validTo: Date
  ) {}

  getName(): string { return "seasonal"; }

  isApplicable(ctx: DiscountContext): boolean {
    return ctx.currentDate >= this.validFrom && ctx.currentDate <= this.validTo;
  }

  calculate(ctx: DiscountContext): number {
    return ctx.order.total * (this.percentage / 100);
  }
}

// Strategy 2: Loyalty discount — based on customer tenure
class LoyaltyDiscountStrategy implements IDiscountStrategy {
  getName(): string { return "loyalty"; }

  isApplicable(ctx: DiscountContext): boolean {
    return ctx.user.accountAgeYears >= 1; // At least 1 year old account
  }

  calculate(ctx: DiscountContext): number {
    const years = ctx.user.accountAgeYears;
    const percentage = years >= 5 ? 15 : years >= 3 ? 10 : 5;
    return ctx.order.total * (percentage / 100);
  }
}

// Strategy 3: Bulk discount — for large quantity orders
class BulkDiscountStrategy implements IDiscountStrategy {
  constructor(private minItems: number = 10, private percentage: number = 10) {}

  getName(): string { return "bulk"; }

  isApplicable(ctx: DiscountContext): boolean {
    const totalItems = ctx.order.items.reduce((sum, item) => sum + item.quantity, 0);
    return totalItems >= this.minItems;
  }

  calculate(ctx: DiscountContext): number {
    return ctx.order.total * (this.percentage / 100);
  }
}

// Strategy 4: Coupon discount — specific code applied
class CouponDiscountStrategy implements IDiscountStrategy {
  constructor(private couponCode: string, private couponService: ICouponService) {}

  getName(): string { return `coupon:${this.couponCode}`; }

  isApplicable(ctx: DiscountContext): boolean {
    return this.couponService.isValid(this.couponCode, ctx.user.id);
  }

  calculate(ctx: DiscountContext): number {
    const coupon = this.couponService.getCoupon(this.couponCode);
    return coupon.type === "fixed"
      ? coupon.value
      : ctx.order.total * (coupon.value / 100);
  }
}

// Strategy 5: No discount — the Null Object Strategy, always safe default
class NoDiscountStrategy implements IDiscountStrategy {
  getName(): string { return "none"; }
  isApplicable(): boolean { return true; }
  calculate(): number { return 0; }
}

// --- 4. THE CONTEXT SERVICE ---

class PricingService {
  private strategy: IDiscountStrategy = new NoDiscountStrategy();

  // Inject a single strategy explicitly
  setStrategy(strategy: IDiscountStrategy): this {
    this.strategy = strategy;
    return this;
  }

  // Look across multiple strategy options and pick the best one for the user
  setBestStrategy(strategies: IDiscountStrategy[], ctx: DiscountContext): this {
    const applicable = strategies.filter(s => s.isApplicable(ctx));
    
    if (applicable.length === 0) {
      this.strategy = new NoDiscountStrategy();
      return this;
    }
    
    // Pick strategy that gives the highest total discount amount to the customer
    this.strategy = applicable.reduce((best, current) =>
      current.calculate(ctx) > best.calculate(ctx) ? current : best
    );
    return this;
  }

  calculateFinalPrice(ctx: DiscountContext): PricingResult {
    if (!this.strategy.isApplicable(ctx)) {
      return { original: ctx.order.total, discount: 0, final: ctx.order.total, strategyUsed: "none" };
    }

    const discount = this.strategy.calculate(ctx);
    const final = Math.max(0, ctx.order.total - discount);

    return {
      original: ctx.order.total,
      discount: discount,
      final: final,
      strategyUsed: this.strategy.getName()
    };
  }
}

// --- 5. DEMONSTRATION RUN ---

async function runAdvancedStrategyDemo(): Promise<void> {
  // Mock implementations for demo data
  const mockCouponService: ICouponService = {
    isValid: (code: string, userId: string) => code === "SAVE50",
    getCoupon: (code: string) => ({ type: "fixed", value: 50.00 })
  };

  const currentOrder: Order = {
    id: "ord_abc123",
    items: [{ id: "item1", quantity: 12 }], // Meets bulk threshold (12 >= 10)
    total: 1000.00
  };

  const currentUser: User = {
    id: "usr_xyz789",
    accountAgeYears: 4 // Meets loyalty threshold (gives 10%)
  };

  const diwaliStart = new Date("2026-10-01");
  const diwaliEnd = new Date("2026-11-15");

  const ctx: DiscountContext = {
    order: currentOrder,
    user: currentUser,
    currentDate: new Date("2026-10-25") // Right in the middle of seasonal sale
  };

  const pricingService = new PricingService();

  console.log("--- Option A: Explicit Strategy Allocation ---");
  pricingService.setStrategy(new LoyaltyDiscountStrategy());
  const loyaltyResult = pricingService.calculateFinalPrice(ctx);
  console.log(`Loyalty calculation: Final price ₹${loyaltyResult.final} via strategy: ${loyaltyResult.strategyUsed}`);

  console.log("\n--- Option B: Automated Runtime 'Best Match' Allocation ---");
  const allStrategies: IDiscountStrategy[] = [
    new SeasonalDiscountStrategy(20, diwaliStart, diwaliEnd), // 20% of 1000 = ₹200 (WINNER)
    new LoyaltyDiscountStrategy(),                             // 10% of 1000 = ₹100
    new BulkDiscountStrategy(10, 10),                         // 10% of 1000 = ₹100
    new CouponDiscountStrategy("SAVE50", mockCouponService)   // Fixed = ₹50
  ];

  pricingService.setBestStrategy(allStrategies, ctx);
  const bestResult = pricingService.calculateFinalPrice(ctx);

  console.log(`Original order amount: ₹${bestResult.original}`);
  console.log(`Discount obtained: ₹${bestResult.discount}`);
  console.log(`Final price: ₹${bestResult.final} (strategy selected: "${bestResult.strategyUsed}")`);
}

runAdvancedStrategyDemo();