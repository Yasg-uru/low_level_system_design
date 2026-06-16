/**
 * ============================================================================
 * BUILDER DESIGN PATTERN: HTTP REQUEST CREATION
 * ============================================================================
 * * Purpose: Separates the complex construction of an HTTP request from its 
 * representation, enabling an intuitive, readable, and fluent API chain.
 * * Components:
 * - Product:         HttpRequest (Immutable object representation)
 * - Builder:         HttpRequestBuilder (Mutable fluent configuration)
 * - Client Usage:    Chained property mutations culminating in a final .build()
 */

// ============================================================================
// 1. TYPES & SUPPORTING INTERFACES
// ============================================================================

export type HttpMethod = 'GET' | 'POST' | 'PUT' | 'DELETE' | 'PATCH' | 'HEAD';

export interface HttpRequestConfig {
  url: string;
  method: HttpMethod;
  headers: Record<string, string>;
  body: unknown;
  timeout: number;
  retries: number;
  retryDelay: number;
  followRedirects: boolean;
  maxRedirects: number;
  validateSsl: boolean;
}

// ============================================================================
// 2. PRODUCT CLASS (The final immutable object)
// ============================================================================

export class HttpRequest {
  // Deeply read-only to guarantee thread safety and immutability after construction
  public readonly url: string;
  public readonly method: HttpMethod;
  public readonly headers: Readonly<Record<string, string>>;
  public readonly body: unknown;
  public readonly timeout: number;
  public readonly retries: number;
  public readonly retryDelay: number;
  public readonly followRedirects: boolean;
  public readonly maxRedirects: number;
  public readonly validateSsl: boolean;

  constructor(config: HttpRequestConfig) {
    this.url = config.url;
    this.method = config.method;
    this.headers = Object.freeze({ ...config.headers });
    this.body = typeof config.body === 'object' && config.body !== null ? Object.freeze(config.body) : config.body;
    this.timeout = config.timeout;
    this.retries = config.retries;
    this.retryDelay = config.retryDelay;
    this.followRedirects = config.followRedirects;
    this.maxRedirects = config.maxRedirects;
    this.validateSsl = config.validateSsl;
  }

  /**
   * Simulates the actual network request dispatch
   */
  public async send(): Promise<Response> {
    console.log(`\n[Network] Initializing ${this.method} request to: "${this.url}"`);
    console.log(`[Network] Headers Active:`, JSON.stringify(this.headers));
    if (this.body) console.log(`[Network] Payload Transmitting:`, JSON.stringify(this.body));
    console.log(`[Network] Policies -> Retries: ${this.retries} | Timeout: ${this.timeout}ms | SSL Verify: ${this.validateSsl}`);
    
    // Returning a mocked Fetch API Response object for runtime safety
    return new Response(JSON.stringify({ status: "success", data: "Mocked API Payload" }), {
      status: 200,
      headers: { "Content-Type": "application/json" }
    });
  }
}

// ============================================================================
// 3. FLUENT BUILDER IMPLEMENTATION
// ============================================================================

export class HttpRequestBuilder {
  // Internal state — mutable ONLY during building steps
  private _url: string = "";
  private _method: HttpMethod = "GET";
  private _headers: Record<string, string> = {};
  private _body: unknown = undefined;
  private _timeout: number = 30_000;
  private _retries: number = 0;
  private _retryDelay: number = 1_000;
  private _followRedirects: boolean = true;
  private _maxRedirects: number = 5;
  private _validateSsl: boolean = true;

  // Each method returns `this` — enables fluent chaining syntax

  public setUrl(url: string): this {
    this._url = url;
    return this;
  }

  public setMethod(method: HttpMethod): this {
    this._method = method;
    return this;
  }

  public addHeader(key: string, value: string): this {
    this._headers[key] = value;
    return this;
  }

  public addHeaders(headers: Record<string, string>): this {
    Object.assign(this._headers, headers);
    return this;
  }

  public setBody(body: unknown): this {
    this._body = body;
    // Auto-set content type if fallback default is required
    if (!this._headers["Content-Type"]) {
      this._headers["Content-Type"] = "application/json";
    }
    return this;
  }

  public setTimeout(ms: number): this {
    if (ms <= 0) throw new Error("Timeout metric must be positive");
    this._timeout = ms;
    return this;
  }

  public withRetries(count: number, delayMs: number = 1_000): this {
    if (count < 0) throw new Error("Retry lifecycle counts cannot be negative");
    this._retries = count;
    this._retryDelay = delayMs;
    return this;
  }

  public withBearerToken(token: string): this {
    return this.addHeader("Authorization", `Bearer ${token}`);
  }

  public withBasicAuth(username: string, password: string): this {
    // Check if running in a Node environment or standard modern runtime environment
    const credentials = `${username}:${password}`;
    const encoded = typeof Buffer !== 'undefined'
      ? Buffer.from(credentials).toString("base64")
      : btoa(credentials);
      
    return this.addHeader("Authorization", `Basic ${encoded}`);
  }

  public withApiKey(key: string, headerName: string = "X-API-Key"): this {
    return this.addHeader(headerName, key);
  }

  public disableSslValidation(): this {
    this._validateSsl = false;
    return this;
  }

  public noRedirects(): this {
    this._followRedirects = false;
    return this;
  }

  /**
   * Validation + Construction logic — The final creation contract step
   */
  public build(): HttpRequest {
    if (!this._url) throw new Error("Target destination URL is mandatory");
    if (!this._url.startsWith("http")) throw new Error("URL scheme structure must be absolute");
    
    if (["POST", "PUT", "PATCH"].includes(this._method) && !this._body) {
      console.warn(`[HttpRequestBuilder] Warning: ${this._method} request initialization was processed without an explicitly defined body payload.`);
    }

    return new HttpRequest({
      url:             this._url,
      method:          this._method,
      headers:         { ...this._headers },
      body:            this._body,
      timeout:         this._timeout,
      retries:         this._retries,
      retryDelay:      this._retryDelay,
      followRedirects: this._followRedirects,
      maxRedirects:    this._maxRedirects,
      validateSsl:     this._validateSsl
    });
  }

  /**
   * Convenience execution handler: seamlessly builds + forwards directly to runtime dispatch
   */
  public async send(): Promise<Response> {
    return this.build().send();
  }
}

// ============================================================================
// 4. DEMONSTRATION / EXECUTION EXAMPLES
// ============================================================================

async function runDemonstration() {
  console.log("=== Builder Design Pattern Active ===");

  // Example 1: Creating an immutable request configuration via explicit build step
  const getProfileRequest = new HttpRequestBuilder()
    .setUrl("https://api.sandbox.internal/v1/users/me")
    .setMethod("GET")
    .withBearerToken("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9")
    .withRetries(3, 500)
    .setTimeout(5000)
    .build();

  console.log("\n-> Product built successfully. Attempting structural dispatch execution...");
  await getProfileRequest.send();

  // Example 2: In-line convenience chaining utilizing short-circuit .send() termination
  console.log("\n-> Executing immediate transaction payload stream using chained handlers...");
  await new HttpRequestBuilder()
    .setUrl("https://api.sandbox.internal/v1/payments/transact")
    .setMethod("POST")
    .withApiKey("sk_live_51NxB8", "X-Secure-Provider-Token")
    .setBody({ orderId: "ORD_99821", totalAmount: 7500 })
    .noRedirects()
    .send();
}

runDemonstration();