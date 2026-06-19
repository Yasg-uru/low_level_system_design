package com.lld.patterns.structural.proxy.protection;

import java.util.List;

public class DocumentUser {
    private final String username;
    private final List<Permission> permissions;

    public DocumentUser(String username, List<Permission> permissions) {
        this.username = username;
        this.permissions = permissions;
    }

    public String getUsername() {
        return username;
    }

    public List<Permission> getPermissions() {
        return permissions;
    }
}
