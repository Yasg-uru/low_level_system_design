// Protection Proxy Example - Access control based on permissions

// Permission levels
enum Permission {
  READ = "READ",
  WRITE = "WRITE",
  ADMIN = "ADMIN"
}

// User interface for document access
interface DocumentUser {
  username: string;
  permissions: Permission[];
}

// Subject Interface
interface Document {
  read(content: string): void;
  write(content: string, newContent: string): void;
  delete(content: string): void;
}

// Real Subject - Contains sensitive operations
class RealDocument implements Document {
  read(content: string): void {
    console.log(`Reading document: "${content}"`);
  }

  write(content: string, newContent: string): void {
    console.log(`Writing to document: "${content}" -> "${newContent}"`);
  }

  delete(content: string): void {
    console.log(`Deleting document: "${content}"`);
  }
}

// Protection Proxy - Checks permissions before allowing access
class ProtectionDocumentProxy implements Document {
  private realDocument: RealDocument;
  private user: DocumentUser;

  constructor(realDocument: RealDocument, user: DocumentUser) {
    this.realDocument = realDocument;
    this.user = user;
  }

  private checkPermission(requiredPermission: Permission): boolean {
    const hasPermission = this.user.permissions.includes(requiredPermission);
    if (!hasPermission) {
      console.log(`Access denied: User "${this.user.username}" does not have ${requiredPermission} permission`);
    }
    return hasPermission;
  }

  read(content: string): void {
    if (this.checkPermission(Permission.READ)) {
      this.realDocument.read(content);
    }
  }

  write(content: string, newContent: string): void {
    if (this.checkPermission(Permission.WRITE)) {
      this.realDocument.write(content, newContent);
    }
  }

  delete(content: string): void {
    if (this.checkPermission(Permission.ADMIN)) {
      this.realDocument.delete(content);
    }
  }
}

// Usage
function protectionProxyDemo(): void {
  console.log("=== Protection Proxy Demo ===\n");

  const realDocument = new RealDocument();

  // Create users with different permission levels
  const readOnlyUser: DocumentUser = {
    username: "reader",
    permissions: [Permission.READ]
  };

  const readWriteUser: DocumentUser = {
    username: "editor",
    permissions: [Permission.READ, Permission.WRITE]
  };

  const adminUser: DocumentUser = {
    username: "admin",
    permissions: [Permission.READ, Permission.WRITE, Permission.ADMIN]
  };

  console.log("--- Read-Only User ---");
  const readOnlyProxy = new ProtectionDocumentProxy(realDocument, readOnlyUser);
  readOnlyProxy.read("Secret Document");
  readOnlyProxy.write("Secret Document", "Updated Content");
  readOnlyProxy.delete("Secret Document");

  console.log("\n--- Read-Write User ---");
  const readWriteProxy = new ProtectionDocumentProxy(realDocument, readWriteUser);
  readWriteProxy.read("Secret Document");
  readWriteProxy.write("Secret Document", "Updated Content");
  readWriteProxy.delete("Secret Document");

  console.log("\n--- Admin User ---");
  const adminProxy = new ProtectionDocumentProxy(realDocument, adminUser);
  adminProxy.read("Secret Document");
  adminProxy.write("Secret Document", "Updated Content");
  adminProxy.delete("Secret Document");
}

// Run the demo
protectionProxyDemo();
