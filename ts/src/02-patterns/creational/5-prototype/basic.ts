/**
 * ============================================================================
 * PROTOTYPE DESIGN PATTERN WITH REGISTRY
 * ============================================================================
 * * Purpose: Creates new objects by copying an existing archetypal instance 
 * (the prototype), significantly reducing overhead when object creation is costly,
 * and decoupling client logic from concrete configuration classes.
 * * Components:
 * - Prototype Interface: IPrototype<T> (Declares cloning capability)
 * - Concrete Prototype:  EmailTemplate (Implements deep copy clone execution)
 * - Prototype Registry:  PrototypeRegistry (Central cached inventory manager)
 * - Clients:             EmailService, SMTPMailer
 */

// ============================================================================
// 1. INTERFACES & SUPPORTING STRUCTURAL TYPES
// ============================================================================

export interface IPrototype<T> {
    clone(): T;
}

export interface User {
    name: string;
    email: string;
}

export interface EmailAttachment {
    filename: string;
    contentType: string;
    data: Buffer;
}

export interface EmailTemplateConfig {
    subject: string;
    htmlBody: string;
    textBody?: string;
    fromAddress: string;
    replyTo?: string;
    headers?: Record<string, string>;
    attachments?: EmailAttachment[];
    metadata?: Record<string, unknown>;
}

// ============================================================================
// 2. CONCRETE PROTOTYPE (Implements Deep Copy Cloning)
// ============================================================================

export class EmailTemplate implements IPrototype<EmailTemplate> {
    private subject: string;
    private htmlBody: string;
    private textBody: string;
    private fromAddress: string;
    private replyTo?: string;
    private headers?: Record<string, string>;
    private attachments?: EmailAttachment[];
    private metadata?: Record<string, unknown>;
    private createdAt: Date = new Date();
    private version: number = 1;

    constructor(config: EmailTemplateConfig) {
        this.subject = config.subject;
        this.htmlBody = config.htmlBody;
        this.textBody = config.textBody ?? this.stripHtml(config.htmlBody);
        this.fromAddress = config.fromAddress;
        this.replyTo = config.replyTo;
        this.headers = config.headers;
        this.attachments = config.attachments;
        this.metadata = config.metadata;
    }

    /**
     * Performs an explicit DEEP COPY operation to insulate the cloned instance 
     * from original state reference mutations.
     */
    public clone(): EmailTemplate {
        const cloned = new EmailTemplate({
            subject: this.subject,
            htmlBody: this.htmlBody,
            textBody: this.textBody,
            fromAddress: this.fromAddress,
            replyTo: this.replyTo,
            // Deep copying nested properties to prevent shared references
            headers: this.headers ? { ...this.headers } : undefined,
            attachments: this.attachments
                ? this.attachments.map((att) => ({
                    filename: att.filename,
                    contentType: att.contentType,
                    data: Buffer.from(att.data),
                }))
                : undefined,
            metadata: this.metadata ? JSON.parse(JSON.stringify(this.metadata)) : undefined,
        });

        cloned.createdAt = new Date(this.createdAt.getTime());
        cloned.version = this.version;
        return cloned;
    }

    // Fluent API mutation step methods
    public withSubject(subject: string): this {
        this.subject = subject;
        return this;
    }

    public withBody(html: string, text?: string): this {
        this.htmlBody = html;
        this.textBody = text ?? this.stripHtml(html);
        return this;
    }

    public withAttachments(attachments: EmailAttachment[]): this {
        if (!this.attachments) this.attachments = [];
        this.attachments.push(...attachments);
        return this;
    }

    public withHeader(key: string, value: string): this {
        if (!this.headers) this.headers = {};
        this.headers[key] = value;
        return this;
    }

    public bumpVersion(): this {
        this.version += 1;
        return this;
    }

    // Getters added to avoid bracket-notation property hacking in clients
    public getDetails() {
        return {
            subject: this.subject,
            htmlBody: this.htmlBody,
            textBody: this.textBody,
            fromAddress: this.fromAddress,
            version: this.version,
        };
    }

    private stripHtml(html: string): string {
        return html.replace(/<[^>]*>/g, '');
    }
}

// ============================================================================
// 3. PROTOTYPE REGISTRY (Centralized Blueprint Cache Store)
// ============================================================================

export class PrototypeRegistry<T extends IPrototype<T>> {
    private prototypes: Map<string, T> = new Map();

    public registerPrototype(name: string, prototype: T): void {
        this.prototypes.set(name, prototype);
    }

    public getPrototype(name: string): T | undefined {
        const prototype = this.prototypes.get(name);
        return prototype ? prototype.clone() : undefined;
    }

    public has(name: string): boolean {
        return this.prototypes.has(name);
    }

    public list(): string[] {
        return [...this.prototypes.keys()];
    }

    public deregister(name: string): boolean {
        return this.prototypes.delete(name);
    }

    public updatePrototype(name: string, prototype: T): boolean {
        if (!this.prototypes.has(name)) return false;
        this.prototypes.set(name, prototype);
        return true;
    }
}

// ============================================================================
// 4. BUSINESS LOGIC & DELIVERY SERVICES
// ============================================================================

export interface IMailer {
    sendEmail(template: EmailTemplate, to: string, variables?: Record<string, unknown>): Promise<void>;
}

export class SMTPMailer implements IMailer {
    public async sendEmail(
        template: EmailTemplate,
        to: string,
        variables?: Record<string, unknown>
    ): Promise<void> {
        const email = template.getDetails();

        console.log(`[SMTP Mailer] Dispatching message to: <${to}>`);
        console.log(`              Subject: "${email.subject}" [v${email.version}]`);
        console.log(`              Payload HTML Length: ${email.htmlBody.length} bytes`);
        if (variables) console.log(`              Injected Context:`, JSON.stringify(variables));
        console.log(`----------------------------------------------------------------`);
    }
}

export class EmailService {
    constructor(
        private readonly registry: PrototypeRegistry<EmailTemplate>,
        private readonly mailer: IMailer
    ) { }

    public async sendWelcome(user: User): Promise<void> {
        const template = this.registry.getPrototype("welcomeEmail");
        if (!template) throw new Error("Prototype baseline 'welcomeEmail' is unconfigured.");

        template
            .withSubject(`Welcome ${user.name}!`)
            .withHeader("X-Custom-Header", "WelcomeEmail")
            .bumpVersion();

        await this.mailer.sendEmail(template, user.email, { userName: user.name });
    }

    public async sendOrderConfirmation(user: User, orderId: string): Promise<void> {
        const template = this.registry.getPrototype("orderConfirmationEmail");
        if (!template) throw new Error("Prototype baseline 'orderConfirmationEmail' is unconfigured.");

        template
            .withSubject(`Order #${orderId} Confirmed!`)
            .withHeader("X-Custom-Header", "OrderConfirmationEmail")
            .bumpVersion();

        await this.mailer.sendEmail(template, user.email, { orderId });
    }

    public async sendPasswordReset(user: User, resetLink: string): Promise<void> {
        const template = this.registry.getPrototype("passwordResetEmail");
        if (!template) throw new Error("Prototype baseline 'passwordResetEmail' is unconfigured.");

        template
            .withSubject(`Password Reset Request for ${user.name}`)
            .withHeader("X-Custom-Header", "PasswordResetEmail")
            .bumpVersion();

        await this.mailer.sendEmail(template, user.email, { resetLink });
    }
}

// ============================================================================
// 5. APPLICATION INITIALIZATION & SEEDING
// ============================================================================

async function initializeApp() {
    const emailRegistry = new PrototypeRegistry<EmailTemplate>();

    // Seeding the default core system blueprints
    emailRegistry.registerPrototype(
        "welcomeEmail",
        new EmailTemplate({
            subject: "Welcome to our service!",
            htmlBody: "<h1>Welcome!</h1><p>Thank you for joining our service.</p>",
            fromAddress: "noreply@ourservice.com",
            metadata: { category: "welcome", priority: "high" },
        })
    );

    emailRegistry.registerPrototype(
        "orderConfirmationEmail",
        new EmailTemplate({
            subject: "Your order has been confirmed!",
            htmlBody: "<h1>Order Confirmed!</h1><p>Your order has been successfully placed.</p>",
            fromAddress: "noreply@ourservice.com",
            metadata: { category: "confirmation", priority: "high" },
        })
    );

    emailRegistry.registerPrototype(
        "passwordResetEmail",
        new EmailTemplate({
            subject: "Password Reset Request",
            htmlBody: "<h1>Password Reset</h1><p>Click the link below to change credentials.</p>",
            fromAddress: "noreply@ourservice.com",
            metadata: { category: "reset", priority: "high" },
        })
    );

    // Runtime Pipeline Demonstration
    const mailer = new SMTPMailer();
    const emailService = new EmailService(emailRegistry, mailer);

    const mockUser: User = {
        name: "Yash Choudhary",
        email: "yash.choudhary@example.com",
    };

    console.log("=== Execution Engine Online: Processing Pipeline Triggers ===\n");

    await emailService.sendWelcome(mockUser);
    await emailService.sendOrderConfirmation(mockUser, "TXN_77491");
    await emailService.sendPasswordReset(mockUser, "https://ourservice.com/reset-password");
}

initializeApp();