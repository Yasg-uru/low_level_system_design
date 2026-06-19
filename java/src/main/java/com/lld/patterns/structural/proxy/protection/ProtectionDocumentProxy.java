package com.lld.patterns.structural.proxy.protection;

public class ProtectionDocumentProxy implements Document {
    private final RealDocument realDocument;
    private final DocumentUser user;

    public ProtectionDocumentProxy(RealDocument realDocument, DocumentUser user) {
        this.realDocument = realDocument;
        this.user = user;
    }

    private boolean checkPermission(Permission requiredPermission) {
        boolean hasPermission = user.getPermissions().contains(requiredPermission);
        if (!hasPermission) {
            System.out.println("Access denied: User \"" + user.getUsername() + "\" does not have " + requiredPermission + " permission");
        }
        return hasPermission;
    }

    @Override
    public void read(String content) {
        if (checkPermission(Permission.READ)) {
            realDocument.read(content);
        }
    }

    @Override
    public void write(String content, String newContent) {
        if (checkPermission(Permission.WRITE)) {
            realDocument.write(content, newContent);
        }
    }

    @Override
    public void delete(String content) {
        if (checkPermission(Permission.ADMIN)) {
            realDocument.delete(content);
        }
    }
}
