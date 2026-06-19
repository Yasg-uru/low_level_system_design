package com.lld.patterns.structural.proxy.remote;

import java.util.Map;

public interface RemoteUserServiceInterface {
    Map<String, Object> getUser(int id) throws Exception;
    Map<String, Object> createUser(Map<String, Object> userData) throws Exception;
}
