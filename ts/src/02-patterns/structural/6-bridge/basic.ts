/**
 * BRIDGE DESIGN PATTERN EXAMPLE (Strictly Typed)
 * * Intent: Decouple an abstraction from its implementation so that the two can vary independently.
 * Fixes: The N × M class explosion problem.
 */

// --- 1. DEFINE RIGOROUS TYPES & INTERFACES ---

interface SendOptions {
  retries: number;
  timeoutMs: number;
}

// Concrete payloads for our mock SDK clients
interface EmailPayload {
  to: string;
  subject: string;
  html: string;
  timeout: number;
}

interface SMSPayload {
  to: string;
  body: string;
}

interface PushPayload {
  token: string;
  title: string;
  body: string;
}

interface WhatsAppPayload {
  to: string;
  text: string;
}

interface IncidentRecord {
  to: string;
  subject: string;
  sentAt: Date;
}

// --- 2. MOCK THIRD-PARTY CLIENT IMPLEMENTATIONS ---

const emailClient = {
  send: async (payload: EmailPayload): Promise<void> => {}
};

const twilioClient = {
  send: async (payload: SMSPayload): Promise<void> => {}
};

const fcmClient = {
  send: async (payload: PushPayload): Promise<void> => {}
};

const whatsappClient = {
  send: async (payload: WhatsAppPayload): Promise<void> => {}
};

const IncidentTracker = {
  record: async (record: IncidentRecord): Promise<void> => {
    console.log(`[Tracker] Logged incident for ${record.to}`);
  }
};

const ON_CALL_PHONE = "+15550199";

// --- 3. THE IMPLEMENTOR INTERFACE ---

interface INotificationSender {
  send(to: string, subject: string, body: string, options: SendOptions): Promise<void>;
}

// --- 4. CONCRETE IMPLEMENTORS (The Delivery Channels) ---

class EmailSender implements INotificationSender {
  async send(to: string, subject: string, body: string, options: SendOptions): Promise<void> {
    console.log(`[Email] To: ${to} | Subject: ${subject} | Retries: ${options.retries}`);
    await emailClient.send({ to, subject, html: body, timeout: options.timeoutMs });
  }
}

class SMSSender implements INotificationSender {
  async send(to: string, subject: string, body: string, options: SendOptions): Promise<void> {
    console.log(`[SMS] To: ${to} | Retries: ${options.retries}`);
    await twilioClient.send({ to, body: `${subject}: ${body}`.slice(0, 160) });
  }
}

class PushSender implements INotificationSender {
  async send(to: string, subject: string, body: string, options: SendOptions): Promise<void> {
    console.log(`[Push] Device: ${to} | Retries: ${options.retries}`);
    await fcmClient.send({ token: to, title: subject, body });
  }
}

class WhatsAppSender implements INotificationSender {
  async send(to: string, subject: string, body: string, options: SendOptions): Promise<void> {
    console.log(`[WhatsApp] To: ${to} | Body: ${body}`);
    await whatsappClient.send({ to, text: `*${subject}*\n${body}` });
  }
}

// --- 5. THE ABSTRACTION ---

abstract class Notification {
  // The crucial "Bridge" reference
  constructor(protected sender: INotificationSender) {}

  abstract send(to: string, subject: string, body: string): Promise<void>;
}

// --- 6. REFINED ABSTRACTIONS (The Business Logic / Urgency Levels) ---

class NormalNotification extends Notification {
  async send(to: string, subject: string, body: string): Promise<void> {
    await this.sender.send(to, subject, body, {
      retries: 1,
      timeoutMs: 5_000
    });
  }
}

class UrgentNotification extends Notification {
  async send(to: string, subject: string, body: string): Promise<void> {
    await this.sender.send(to, `🚨 URGENT: ${subject}`, body, {
      retries: 5,
      timeoutMs: 15_000
    });
    await IncidentTracker.record({ to, subject, sentAt: new Date() });
  }
}

class CriticalNotification extends Notification {
  constructor(sender: INotificationSender, private escalationSender: INotificationSender) {
    super(sender);
  }

  async send(to: string, subject: string, body: string): Promise<void> {
    await this.sender.send(to, `🔴 CRITICAL: ${subject}`, body, {
      retries: 10,
      timeoutMs: 30_000
    });

    await this.escalationSender.send(
      ON_CALL_PHONE,
      `CRITICAL ESCALATION: ${subject}`,
      body,
      { retries: 10, timeoutMs: 30_000 }
    );
  }
}

// --- 7. RUNTIME USAGE SERVICE ---

type UrgencyLevel = "normal" | "urgent" | "critical";
type ChannelType = "email" | "sms" | "push" | "whatsapp";

class AlertService {
  private channelMap: Record<ChannelType, INotificationSender> = {
    email:    new EmailSender(),
    sms:      new SMSSender(),
    push:     new PushSender(),
    whatsapp: new WhatsAppSender()
  };

  async alert(
    urgency: UrgencyLevel,
    channel: ChannelType,
    to: string,
    subject: string,
    body: string
  ): Promise<void> {
    const sender = this.channelMap[channel];

    const notification: Notification =
      urgency === "critical" ? new CriticalNotification(sender, this.channelMap["sms"]) // Defaulting escalation to SMS safely
      : urgency === "urgent" ? new UrgentNotification(sender)
      : new NormalNotification(sender);

    await notification.send(to, subject, body);
  }
}

// --- Verification Run ---
async function runDemo(): Promise<void> {
  console.log("--- Manual Combinations ---");
  const normalEmail   = new NormalNotification(new EmailSender());
  const urgentSMS       = new UrgentNotification(new SMSSender());
  const criticalSMS     = new CriticalNotification(new SMSSender(), new PushSender());

  await normalEmail.send("user@test.com", "Order shipped", "Your order is on its way");
  await urgentSMS.send("+919876543210", "Payment failed", "Please update your card");
  await criticalSMS.send("+919876543210", "Server down", "Production database unreachable");

  console.log("\n--- Service-Driven (Runtime) Combinations ---");
  const alerts = new AlertService();
  await alerts.alert("critical", "email", "oncall@company.com", "DB down", "Immediate action needed");
  await alerts.alert("normal",   "push",  "device_token_xyz",   "New comment", "Someone replied");
}

runDemo();