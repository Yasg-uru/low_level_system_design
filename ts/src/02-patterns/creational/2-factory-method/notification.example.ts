/**
 * Factory Method Example: Notification System
 *
 * Creates different types of notifications
 * without client knowing concrete implementations.
 */

interface INotification {
  send(message: string): void;
}

class EmailNotification implements INotification {
  constructor(private email: string) {}

  send(message: string): void {
    console.log(`📧 Sending email to ${this.email}: "${message}"`);
  }
}

class SMSNotification implements INotification {
  constructor(private phoneNumber: string) {}

  send(message: string): void {
    console.log(`📱 Sending SMS to ${this.phoneNumber}: "${message}"`);
  }
}

class PushNotification implements INotification {
  constructor(private deviceId: string) {}

  send(message: string): void {
    console.log(`🔔 Sending push to device ${this.deviceId}: "${message}"`);
  }
}

// Factory
abstract class NotificationFactory {
  abstract createNotification(): INotification;

  sendNotification(message: string): void {
    const notification = this.createNotification();
    notification.send(message);
  }
}

class EmailNotificationFactory extends NotificationFactory {
  constructor(private email: string) {
    super();
  }

  createNotification(): INotification {
    return new EmailNotification(this.email);
  }
}

class SMSNotificationFactory extends NotificationFactory {
  constructor(private phoneNumber: string) {
    super();
  }

  createNotification(): INotification {
    return new SMSNotification(this.phoneNumber);
  }
}

class PushNotificationFactory extends NotificationFactory {
  constructor(private deviceId: string) {
    super();
  }

  createNotification(): INotification {
    return new PushNotification(this.deviceId);
  }
}

// Usage
const emailFactory = new EmailNotificationFactory('user@example.com');
emailFactory.sendNotification('Welcome!');

const smsFactory = new SMSNotificationFactory('+1234567890');
smsFactory.sendNotification('Your code is 123456');

const pushFactory = new PushNotificationFactory('device-123');
pushFactory.sendNotification('New message received');
