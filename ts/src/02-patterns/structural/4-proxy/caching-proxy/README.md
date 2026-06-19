# Caching Proxy

## Overview
A Caching Proxy stores the results of expensive operations and returns cached results for identical requests, avoiding repeated expensive calls.

## When to Use
- When operations are expensive to perform (database queries, API calls, complex calculations)
- When the same requests are made frequently
- When the results don't change frequently
- When you want to improve performance by reducing redundant operations

## Benefits
- **Performance**: Avoids repeated expensive operations
- **Reduced Load**: Decreases load on external systems (databases, APIs)
- **Faster Response**: Returns cached results instantly
- **Cost Savings**: Reduces API call costs or database query load

## Example Scenario
Caching database query results - instead of querying the database every time for the same data, the proxy returns cached results.

## Structure
```
Subject (Interface)
    ↓
RealSubject (Performs expensive operations)
    ↓
Proxy (Caching Proxy - stores and returns cached results)
```

## Common Mistakes

❌ **Bad approach (Integrating Cache directly inside the Core Service)**
```typescript
// Service class is cluttered with caching variables, checks, and storage, violating SRP.
class RealDataService implements DataService {
  private cache = new Map<string, any>();

  async getData(id: string): Promise<any> {
    if (this.cache.has(id)) {
      return this.cache.get(id);
    }
    const data = await db.query(id);
    this.cache.set(id, data);
    return data;
  }
}
```

✅ **Good approach (Using Caching Proxy)**
```typescript
// Real service only queries the database, while the proxy handles cache storage and retrieval.
class RealDataService implements DataService {
  async getData(id: string): Promise<any> {
    return await db.query(id);
  }
}

class CachingDataServiceProxy implements DataService {
  private cache = new Map<string, any>();
  constructor(private realService: RealDataService) {}

  async getData(id: string): Promise<any> {
    if (this.cache.has(id)) return this.cache.get(id);
    const data = await this.realService.getData(id);
    this.cache.set(id, data);
    return data;
  }
}
```

## Key Points
- The proxy maintains a cache (Map, object, or external cache)
- Before forwarding a request, the proxy checks if the result is cached
- If cached, returns the stored result without calling the real subject
- If not cached, calls the real subject and stores the result
- Cache invalidation strategies may be needed (time-based, size-based, manual)
