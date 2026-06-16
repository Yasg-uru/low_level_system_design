package com.lld.patterns.creational.builder;

public class Response {
    private final int statusCode;
    private final String body;
    private final java.util.Map<String, String> headers;

    public Response(int statusCode, String body, java.util.Map<String, String> headers) {
        this.statusCode = statusCode;
        this.body = body;
        this.headers = java.util.Collections.unmodifiableMap(new java.util.HashMap<>(headers));
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getBody() {
        return body;
    }

    public java.util.Map<String, String> getHeaders() {
        return headers;
    }

    @Override
    public String toString() {
        return "Response{statusCode=" + statusCode + ", body='" + body + "', headers=" + headers + "}";
    }
}
