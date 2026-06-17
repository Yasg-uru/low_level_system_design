class RedisClient {
    constructor(private host: string, private port: number) { }

    connect() {
        console.log(`Connecting to Redis at ${this.host}:${this.port}`);
    }

    read(key: string): string | null {

    }
    write(key: string, value: string): void {

    }
}

interface Cache {
    get(key: string): string | null;
    set(key: string, value: string): void;
}
class RedisCacheAdapter implements Cache {
    private redisClient: RedisClient;

    constructor(host: string, port: number) {
        this.redisClient = new RedisClient(host, port);
        this.redisClient.connect();
    }

    get(key: string): string | null {
        return this.redisClient.read(key);
    }

    set(key: string, value: string): void {
        this.redisClient.write(key, value);
    }
}
class OrderService{
    constructor(private cache: Cache) { }

    placeOrder(userId: string, orderData: any) {
        // Process order logic
        this.cache.set(`order:${userId}`, JSON.stringify(orderData));
    }

    getOrder(userId: string): any {
        const data = this.cache.get(`order:${userId}`);
        return data ? JSON.parse(data) : null;
    }
}

// Usage
const cache: Cache = new RedisCacheAdapter("localhost", 6379);
const orderService = new OrderService(cache); 
cache.set("user:123", "John Doe");
const user = cache.get("user:123");
console.log(user); // Output: John Doe

