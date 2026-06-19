// Caching Proxy Example - Storing results to avoid repeated expensive calls

// Subject Interface
interface DataService {
  getData(id: string): Promise<any>;
}

// Real Subject - Performs expensive operations
class RealDataService implements DataService {
  async getData(id: string): Promise<any> {
    console.log(`Fetching data for ID: ${id} from database... (expensive operation)`);
    
    // Simulate expensive database call
    await new Promise(resolve => setTimeout(resolve, 1000));
    
    // Simulate returning data
    const data = {
      id: id,
      name: `Item ${id}`,
      value: Math.floor(Math.random() * 100),
      timestamp: new Date().toISOString()
    };
    
    console.log(`Data fetched for ID: ${id}`);
    return data;
  }
}

// Caching Proxy - Stores and returns cached results
class CachingDataServiceProxy implements DataService {
  private realService: RealDataService;
  private cache: Map<string, any>;

  constructor(realService: RealDataService) {
    this.realService = realService;
    this.cache = new Map();
  }

  async getData(id: string): Promise<any> {
    // Check if data is already cached
    if (this.cache.has(id)) {
      console.log(`Cache HIT for ID: ${id} - returning cached data`);
      return this.cache.get(id);
    }

    console.log(`Cache MISS for ID: ${id} - fetching from real service`);
    
    // Fetch from real service
    const data = await this.realService.getData(id);
    
    // Store in cache
    this.cache.set(id, data);
    console.log(`Data cached for ID: ${id}`);
    
    return data;
  }

  // Optional: Clear cache
  clearCache(): void {
    this.cache.clear();
    console.log("Cache cleared");
  }

  // Optional: Get cache stats
  getCacheStats(): { size: number; keys: string[] } {
    return {
      size: this.cache.size,
      keys: Array.from(this.cache.keys())
    };
  }
}

// Usage
async function cachingProxyDemo(): Promise<void> {
  console.log("=== Caching Proxy Demo ===\n");

  const realService = new RealDataService();
  const proxyService = new CachingDataServiceProxy(realService);

  console.log("First request - will fetch from database:");
  await proxyService.getData("item1");

  console.log("\nSecond request (same ID) - will use cache:");
  await proxyService.getData("item1");

  console.log("\nThird request (different ID) - will fetch from database:");
  await proxyService.getData("item2");

  console.log("\nFourth request (item1 again) - will use cache:");
  await proxyService.getData("item1");

  console.log("\nFifth request (item2 again) - will use cache:");
  await proxyService.getData("item2");

  console.log("\nCache stats:");
  console.log(proxyService.getCacheStats());

  console.log("\nClearing cache and making another request:");
  proxyService.clearCache();
  await proxyService.getData("item1");
}

// Run the demo
cachingProxyDemo();
