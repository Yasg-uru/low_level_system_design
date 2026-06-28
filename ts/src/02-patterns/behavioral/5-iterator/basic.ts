/**
 * ITERATOR DESIGN PATTERN EXAMPLE
 * * Intent: Provide a way to access the elements of an aggregate object 
 * sequentially without exposing its underlying representation.
 * * Key Benefit: Decouples sequential collection consumption logic from underlying mechanics (Array vs. Paginated DB Streams).
 */

// --- 1. CORE DOMAIN TYPES & INTERFACES ---

interface Order {
  id: string;
  total: number;
  status: "pending" | "completed" | "cancelled";
  createdAt: Date;
}

interface ReportRow {
  id: string;
  total: number;
  date: Date;
}

interface ReportData {
  rows: ReportRow[];
  generatedAt: Date;
}

// --- 2. THE ITERATOR INTERFACES ---

// Standard Synchronous Iterator
interface IIterator<T> {
  hasNext(): boolean;
  next(): T;
  reset(): void;
  peek(): T | null;
}

// Async Variant for Network/Database Paginated Stream Access
interface IAsyncIterator<T> {
  hasNext(): Promise<boolean>;
  next(): Promise<T>;
  reset(): void;
}

// --- 3. MOCK DATA LAYER ---

class OrderRepository {
  private mockData: Order[] = Array.from({ length: 15 }, (_, i) => ({
    id: `ord_${i + 1}`,
    total: (i + 1) * 75.00,
    status: i % 3 === 0 ? "cancelled" : "completed",
    createdAt: new Date()
  }));

  async fetchPage(userId: string, pageIndex: number, pageSize: number): Promise<Order[]> {
    if (!userId) return [];
    const start = pageIndex * pageSize;
    const end = start + pageSize;
    return this.mockData.slice(start, end);
  }
}

// --- 4. CONCRETE ITERATORS ---

// Iterator 1: Simple In-Memory Array Iterator
class ArrayIterator<T> implements IIterator<T> {
  private index = 0;
  constructor(private items: T[]) {}

  hasNext(): boolean {
    return this.index < this.items.length;
  }

  next(): T {
    if (!this.hasNext()) throw new Error("No more elements");
    return this.items[this.index++];
  }

  reset(): void {
    this.index = 0;
  }

  peek(): T | null {
    return this.hasNext() ? this.items[this.index] : null;
  }
}

// Iterator 2: Database Pagination Async Iterator
class PaginatedOrderIterator implements IAsyncIterator<Order> {
  private currentPage: Order[] = [];
  private pageIndex = 0;
  private itemIndex = 0;
  private exhausted = false;

  constructor(
    private repo: OrderRepository,
    private userId: string,
    private pageSize: number = 5
  ) {}

  async hasNext(): Promise<boolean> {
    if (this.itemIndex < this.currentPage.length) return true;
    if (this.exhausted) return false;

    await this.fetchNextPage();
    return this.currentPage.length > 0;
  }

  async next(): Promise<Order> {
    if (!(await this.hasNext())) throw new Error("No more elements");
    return this.currentPage[this.itemIndex++];
  }

  reset(): void {
    this.currentPage = [];
    this.pageIndex = 0;
    this.exhausted = false;
    this.itemIndex = 0;
  }

  private async fetchNextPage(): Promise<void> {
    const data = await this.repo.fetchPage(this.userId, this.pageIndex, this.pageSize);
    if (data.length === 0) {
      this.exhausted = true;
      this.currentPage = [];
      return;
    }
    this.currentPage = data;
    this.pageIndex++;
    this.itemIndex = 0;
  }
}

// Iterator 3: Structural Filter / Guard Wrapper Iterator
class FilteredIterator<T> implements IIterator<T> {
  private nextItem: T | null = null;
  private fetched = false;

  constructor(
    private inner: IIterator<T>,
    private predicate: (item: T) => boolean
  ) {}

  hasNext(): boolean {
    if (this.fetched) return this.nextItem !== null;
    this.fetchNext();
    return this.nextItem !== null;
  }

  next(): T {
    if (!this.fetched) this.fetchNext();
    if (this.nextItem === null) throw new Error("No more elements");
    const item = this.nextItem;
    this.nextItem = null;
    this.fetched = false;
    return item;
  }

  private fetchNext(): void {
    this.fetched = true;
    this.nextItem = null;
    while (this.inner.hasNext()) {
      const candidate = this.inner.next();
      if (this.predicate(candidate)) {
        this.nextItem = candidate;
        return;
      }
    }
  }

  reset(): void {
    this.inner.reset();
    this.nextItem = null;
    this.fetched = false;
  }

  peek(): T | null {
    this.hasNext();
    return this.nextItem;
  }
}

// --- 5. UNIFIED CONSUMPTION SERVICE ---

class OrderReportService {
  // Can process any collection type that exposes the clean Async Iterator contract interface
  async generateReport(iterator: IAsyncIterator<Order> | IIterator<Order>): Promise<ReportData> {
    const rows: ReportRow[] = [];

    const isAsync = (it: any): it is IAsyncIterator<Order> => typeof it.hasNext().then === "function";

    if (isAsync(iterator)) {
      while (await iterator.hasNext()) {
        const order = await iterator.next();
        rows.push({ id: order.id, total: order.total, date: order.createdAt });
      }
    } else {
      while (iterator.hasNext()) {
        const order = iterator.next();
        rows.push({ id: order.id, total: order.total, date: order.createdAt });
      }
    }

    return { rows, generatedAt: new Date() };
  }
}

// --- 6. DEMONSTRATION RUN ---

async function runIteratorDemo(): Promise<void> {
  const orderRepo = new OrderRepository();
  const reportService = new OrderReportService();

  // 1. Array Execution Data Source
  const recentOrders: Order[] = [
    { id: "local_1", total: 150.00, status: "completed", createdAt: new Date() },
    { id: "local_2", total: 240.00, status: "cancelled", createdAt: new Date() }
  ];

  console.log("--- Report From Local Array Data Source ---");
  const inMemoryReport = await reportService.generateReport(new ArrayIterator(recentOrders));
  console.log(`Generated lines: ${inMemoryReport.rows.length}`);

  // 2. Paginated Remote Repository Stream Source
  console.log("\n--- Report From Remote Paginated DB Source ---");
  const paginatedIterator = new PaginatedOrderIterator(orderRepo, "user_123", 5);
  const dbReport = await reportService.generateReport(paginatedIterator);
  console.log(`Generated database lines: ${dbReport.rows.length}`);

  // 3. Filtered Sync Iterator Operations pipeline
  console.log("\n--- Filtered Report (Only Cancelled Items) ---");
  const filteredIterator = new FilteredIterator(
    new ArrayIterator(recentOrders),
    order => order.status === "cancelled"
  );
  const cancelledReport = await reportService.generateReport(filteredIterator);
  console.log(`Cancelled lines extracted: ${cancelledReport.rows.length}`);
}

runIteratorDemo();

export {};