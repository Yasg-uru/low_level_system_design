# Remote Proxy

## Overview
A Remote Proxy represents an object that lives in a different address space, typically on a different server or machine. It acts as a local representative of a remote object, handling the communication details between the client and the remote object.

## When to Use
- When you need to access objects on a different server or machine
- When you want to hide the complexity of network communication
- When you need to provide a local interface to remote services
- When you want to add location transparency (client doesn't need to know if object is local or remote)

## Benefits
- **Location Transparency**: Client code doesn't need to know if the object is local or remote
- **Simplified Communication**: Handles network protocols, serialization, and error handling
- **Decoupling**: Client is decoupled from the remote service implementation
- **Error Handling**: Can handle network failures, retries, and fallbacks

## Example Scenario
A client application accessing a database or web service on a remote server - the remote proxy handles HTTP requests, serialization, and network errors.

## Structure
```
Subject (Interface)
    ↓
RealSubject (Lives on remote server)
    ↓
Proxy (Remote Proxy - Handles communication with remote object)
```

## Common Mistakes

❌ **Bad approach (Writing network boilerplate code inside every client request invocation)**
```typescript
// Clients must manually setup fetch calls, handle serialization, parse status codes, and handle timeouts.
class UserProfilePage {
  async render(userId: number) {
    try {
      const response = await fetch(`https://api.example.com/users/${userId}`);
      const userData = await response.json();
      // Render user UI...
    } catch (e) {
      console.error("Network error", e);
    }
  }
}
```

✅ **Good approach (Encapsulating network communication behind a Remote Proxy)**
```typescript
// Client interacts with a local interface. The proxy encapsulates HTTP transport and error boundaries.
class UserServiceProxy implements RemoteUserServiceInterface {
  async getUser(id: number): Promise<any> {
    const response = await fetch(`https://api.example.com/users/${id}`);
    if (!response.ok) throw new Error("Network request failed");
    return await response.json();
  }
}

class UserProfilePage {
  constructor(private userService: RemoteUserServiceInterface) {}

  async render(userId: number) {
    try {
      const userData = await this.userService.getUser(userId);
      // Render user UI...
    } catch (e) {
      console.error("Failed to load user", e);
    }
  }
}
```

## Key Points
- The proxy implements the same interface as the remote object
- The proxy handles serialization/deserialization of data
- The proxy manages network communication (HTTP, RPC, etc.)
- The proxy can handle retries, timeouts, and error recovery
- The client interacts with the proxy as if it were a local object
