// Remote Proxy Example - Representing an object on a different server

// Subject Interface
interface RemoteUserServiceInterface {
  getUser(id: number): Promise<any>;
  createUser(userData: any): Promise<any>;
}

// Real Subject (Simulated - would be on a remote server)
class RemoteUserService implements RemoteUserServiceInterface {
  private baseUrl: string;

  constructor(baseUrl: string) {
    this.baseUrl = baseUrl;
  }

  async getUser(id: number): Promise<any> {
    console.log(`[REMOTE] Fetching user ${id} from ${this.baseUrl}/users/${id}`);
    
    // Simulate network delay
    await new Promise(resolve => setTimeout(resolve, 500));
    
    // Simulate API response
    return {
      id: id,
      name: `User ${id}`,
      email: `user${id}@example.com`,
      createdAt: new Date().toISOString()
    };
  }

  async createUser(userData: any): Promise<any> {
    console.log(`[REMOTE] Creating user on ${this.baseUrl}/users`);
    
    // Simulate network delay
    await new Promise(resolve => setTimeout(resolve, 500));
    
    // Simulate API response
    return {
      id: Math.floor(Math.random() * 1000),
      ...userData,
      createdAt: new Date().toISOString()
    };
  }
}

// Remote Proxy - Handles communication with remote service
class UserServiceProxy implements RemoteUserServiceInterface {
  private remoteService: RemoteUserService;
  private requestCount: number = 0;

  constructor(baseUrl: string) {
    this.remoteService = new RemoteUserService(baseUrl);
  }

  private async makeRequest<T>(operation: string, requestFn: () => Promise<T>): Promise<T> {
    this.requestCount++;
    console.log(`[PROXY] Request #${this.requestCount}: ${operation}`);
    
    try {
      const result = await requestFn();
      console.log(`[PROXY] Request #${this.requestCount} succeeded`);
      return result;
    } catch (error) {
      console.log(`[PROXY] Request #${this.requestCount} failed:`, error);
      throw error;
    }
  }

  async getUser(id: number): Promise<any> {
    return this.makeRequest(`getUser(${id})`, () => this.remoteService.getUser(id));
  }

  async createUser(userData: any): Promise<any> {
    return this.makeRequest(`createUser(${JSON.stringify(userData)})`, () => 
      this.remoteService.createUser(userData)
    );
  }

  getRequestCount(): number {
    return this.requestCount;
  }
}

// Usage
async function remoteProxyDemo(): Promise<void> {
  console.log("=== Remote Proxy Demo ===\n");

  // Create proxy pointing to remote service
  const userService = new UserServiceProxy("https://api.example.com");

  console.log("--- Fetching user from remote server ---");
  const user1 = await userService.getUser(1);
  console.log("Received user:", user1);

  console.log("\n--- Fetching another user ---");
  const user2 = await userService.getUser(2);
  console.log("Received user:", user2);

  console.log("\n--- Creating new user on remote server ---");
  const newUser = await userService.createUser({
    name: "John Doe",
    email: "john.doe@example.com"
  });
  console.log("Created user:", newUser);

  console.log(`\n--- Total requests made: ${userService.getRequestCount()} ---`);
}

// Run the demo
remoteProxyDemo();
