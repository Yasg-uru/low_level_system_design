package com.lld.patterns.structural.facade;

public class Response {
    private int status = 200;
    private Object jsonPayload;

    public Response status(int code) {
        this.status = code;
        return this;
    }

    public void json(Object data) {
        this.jsonPayload = data;
    }

    public int getStatus() { return status; }
    public Object getJsonPayload() { return jsonPayload; }
}
