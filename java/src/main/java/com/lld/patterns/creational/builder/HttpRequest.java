package com.lld.patterns.creational.builder;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class HttpRequest {
    private final String url;
    private final HttpMethod method;
    private final Map<String, String> headers;
    private final Object body;
    private final int timeout;
    private final int retries;
    private final int retryDelay;
    private final boolean followRedirects;
    private final int maxRedirects;
    private final boolean validateSsl;

    // Private constructor enforces creation only via Builder
    private HttpRequest(Builder builder) {
        this.url = builder.url;
        this.method = builder.method;
        this.headers = Collections.unmodifiableMap(new HashMap<>(builder.headers));
        this.body = builder.body;
        this.timeout = builder.timeout;
        this.retries = builder.retries;
        this.retryDelay = builder.retryDelay;
        this.followRedirects = builder.followRedirects;
        this.maxRedirects = builder.maxRedirects;
        this.validateSsl = builder.validateSsl;
    }

    public String getUrl() { return url; }
    public HttpMethod getMethod() { return method; }
    public Map<String, String> getHeaders() { return headers; }
    public Object getBody() { return body; }
    public int getTimeout() { return timeout; }
    public int getRetries() { return retries; }
    public int getRetryDelay() { return retryDelay; }
    public boolean isFollowRedirects() { return followRedirects; }
    public int getMaxRedirects() { return maxRedirects; }
    public boolean isValidateSsl() { return validateSsl; }

    public Response send() {
        System.out.println("\n[Network] Initializing " + this.method + " request to: \"" + this.url + "\"");
        System.out.println("[Network] Headers Active: " + this.headers);
        if (this.body != null) {
            System.out.println("[Network] Payload Transmitting: " + this.body);
        }
        System.out.println("[Network] Policies -> Retries: " + this.retries 
                + " | Timeout: " + this.timeout + "ms | SSL Verify: " + this.validateSsl);
        
        Map<String, String> responseHeaders = new HashMap<>();
        responseHeaders.put("Content-Type", "application/json");
        return new Response(200, "{\"status\": \"success\", \"data\": \"Mocked API Payload\"}", responseHeaders);
    }

    // ============================================================================
    // FLUENT BUILDER IMPLEMENTATION (Static Inner Class)
    // ============================================================================
    public static class Builder {
        private String url = "";
        private HttpMethod method = HttpMethod.GET;
        private final Map<String, String> headers = new HashMap<>();
        private Object body = null;
        private int timeout = 30_000;
        private int retries = 0;
        private int retryDelay = 1_000;
        private boolean followRedirects = true;
        private int maxRedirects = 5;
        private boolean validateSsl = true;

        public Builder setUrl(String url) {
            this.url = url;
            return this;
        }

        public Builder setMethod(HttpMethod method) {
            this.method = method;
            return this;
        }

        public Builder addHeader(String key, String value) {
            this.headers.put(key, value);
            return this;
        }

        public Builder addHeaders(Map<String, String> headers) {
            this.headers.putAll(headers);
            return this;
        }

        public Builder setBody(Object body) {
            this.body = body;
            if (!this.headers.containsKey("Content-Type")) {
                this.headers.put("Content-Type", "application/json");
            }
            return this;
        }

        public Builder setTimeout(int ms) {
            if (ms <= 0) {
                throw new IllegalArgumentException("Timeout metric must be positive");
            }
            this.timeout = ms;
            return this;
        }

        public Builder withRetries(int count) {
            return withRetries(count, 1_000);
        }

        public Builder withRetries(int count, int delayMs) {
            if (count < 0) {
                throw new IllegalArgumentException("Retry lifecycle counts cannot be negative");
            }
            this.retries = count;
            this.retryDelay = delayMs;
            return this;
        }

        public Builder withBearerToken(String token) {
            return addHeader("Authorization", "Bearer " + token);
        }

        public Builder withBasicAuth(String username, String password) {
            String credentials = username + ":" + password;
            String encoded = Base64.getEncoder().encodeToString(credentials.getBytes(StandardCharsets.UTF_8));
            return addHeader("Authorization", "Basic " + encoded);
        }

        public Builder withApiKey(String key) {
            return withApiKey(key, "X-API-Key");
        }

        public Builder withApiKey(String key, String headerName) {
            return addHeader(headerName, key);
        }

        public Builder disableSslValidation() {
            this.validateSsl = false;
            return this;
        }

        public Builder noRedirects() {
            this.followRedirects = false;
            return this;
        }

        public HttpRequest build() {
            if (this.url == null || this.url.isEmpty()) {
                throw new IllegalStateException("Target destination URL is mandatory");
            }
            if (!this.url.startsWith("http")) {
                throw new IllegalStateException("URL scheme structure must be absolute");
            }
            if ((this.method == HttpMethod.POST || this.method == HttpMethod.PUT || this.method == HttpMethod.PATCH) 
                    && this.body == null) {
                System.out.println("[HttpRequestBuilder] Warning: " + this.method 
                        + " request initialization was processed without an explicitly defined body payload.");
            }
            return new HttpRequest(this);
        }

        public Response send() {
            return this.build().send();
        }
    }
}
