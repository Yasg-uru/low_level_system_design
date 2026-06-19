package com.lld.patterns.structural.proxy.remote;

import java.util.Map;
import java.util.concurrent.Callable;

public class UserServiceProxy implements RemoteUserServiceInterface {
    private final RemoteUserService remoteService;
    private int requestCount = 0;

    public UserServiceProxy(String baseUrl) {
        this.remoteService = new RemoteUserService(baseUrl);
    }

    private <T> T makeRequest(String operation, Callable<T> requestFn) throws Exception {
        this.requestCount++;
        System.out.println("[PROXY] Request #" + this.requestCount + ": " + operation);
        
        try {
            T result = requestFn.call();
            System.out.println("[PROXY] Request #" + this.requestCount + " succeeded");
            return result;
        } catch (Exception error) {
            System.out.println("[PROXY] Request #" + this.requestCount + " failed: " + error.getMessage());
            throw error;
        }
    }

    @Override
    public Map<String, Object> getUser(int id) throws Exception {
        return makeRequest("getUser(" + id + ")", () -> remoteService.getUser(id));
    }

    @Override
    public Map<String, Object> createUser(Map<String, Object> userData) throws Exception {
        return makeRequest("createUser(" + userData + ")", () -> remoteService.createUser(userData));
    }

    public int getRequestCount() {
        return this.requestCount;
    }
}
