# Protection Proxy

## Overview
A Protection Proxy controls access to an object based on permissions or access rights. It acts as a security layer, checking if the client has the necessary permissions before allowing access to the real subject.

## When to Use
- When you need to control access to sensitive objects or operations
- When different users have different permission levels
- When you want to implement access control without modifying the real subject
- When you need to validate credentials before allowing operations

## Benefits
- **Security**: Prevents unauthorized access to sensitive operations
- **Separation of Concerns**: Access control logic is separated from business logic
- **Flexibility**: Easy to modify permission rules without changing the real subject
- **Centralized Control**: Single point for access control logic

## Example Scenario
A file system where users have different permission levels (read, write, admin) - the proxy checks permissions before allowing file operations.

## Structure
```
Subject (Interface)
    ↓
RealSubject (Contains sensitive operations)
    ↓
Proxy (Protection Proxy - checks permissions before forwarding requests)
```

## Common Mistakes

❌ **Bad approach (Hardcoding permission checks directly inside business classes)**
```typescript
// Core class is coupled with user roles and session checks, making it hard to test or reuse.
class RealDocument implements Document {
  delete(content: string, userRole: string): void {
    if (userRole !== "ADMIN") {
      throw new Error("Access Denied");
    }
    console.log(`Deleting document: "${content}"`);
  }
}
```

✅ **Good approach (Using Protection Proxy)**
```typescript
// Core class is completely focused on the delete action, while the proxy checks roles.
class RealDocument implements Document {
  delete(content: string): void {
    console.log(`Deleting document: "${content}"`);
  }
}

class ProtectionDocumentProxy implements Document {
  constructor(private realDocument: RealDocument, private userRole: string) {}

  delete(content: string): void {
    if (this.userRole !== "ADMIN") {
      throw new Error("Access Denied");
    }
    this.realDocument.delete(content);
  }
}
```

## Key Points
- The proxy checks permissions before forwarding requests to the real subject
- Permission checks can be based on user roles, credentials, or other criteria
- The proxy can throw errors or return default values for unauthorized access
- The real subject remains unaware of the access control mechanism
- Multiple permission levels can be implemented (read-only, read-write, admin, etc.)
