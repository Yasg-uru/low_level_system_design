package com.lld.patterns.structural.proxy.protection;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Protection Proxy Demo ===");

        RealDocument realDocument = new RealDocument();

        // Create users with different permission levels
        DocumentUser readOnlyUser = new DocumentUser("reader", List.of(Permission.READ));
        DocumentUser readWriteUser = new DocumentUser("editor", List.of(Permission.READ, Permission.WRITE));
        DocumentUser adminUser = new DocumentUser("admin", List.of(Permission.READ, Permission.WRITE, Permission.ADMIN));

        System.out.println("--- Read-Only User ---");
        Document readOnlyProxy = new ProtectionDocumentProxy(realDocument, readOnlyUser);
        readOnlyProxy.read("Secret Document");
        readOnlyProxy.write("Secret Document", "Updated Content");
        readOnlyProxy.delete("Secret Document");

        System.out.println("\n--- Read-Write User ---");
        Document readWriteProxy = new ProtectionDocumentProxy(realDocument, readWriteUser);
        readWriteProxy.read("Secret Document");
        readWriteProxy.write("Secret Document", "Updated Content");
        readWriteProxy.delete("Secret Document");

        System.out.println("\n--- Admin User ---");
        Document adminProxy = new ProtectionDocumentProxy(realDocument, adminUser);
        adminProxy.read("Secret Document");
        adminProxy.write("Secret Document", "Updated Content");
        adminProxy.delete("Secret Document");
    }
}
