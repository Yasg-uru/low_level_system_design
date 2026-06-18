package com.lld.patterns.structural.facade;

import java.util.Map;

public class Request {
    private final User user;
    private final Map<String, Object> body;
    private final Map<String, String> params;

    public Request(User user, Map<String, Object> body, Map<String, String> params) {
        this.user = user;
        this.body = body;
        this.params = params;
    }

    public User getUser() { return user; }
    public Map<String, Object> getBody() { return body; }
    public Map<String, String> getParams() { return params; }
}
