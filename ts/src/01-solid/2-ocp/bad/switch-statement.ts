/**
 * ANTI-PATTERN: Open for modification (Violates OCP)
 *
 * Adding new features requires modifying existing code.
 * Hard to extend without breaking existing functionality.
 */

enum PaymentType {
  CREDIT_CARD = 'credit_card',
  PAYPAL = 'paypal',
  BANK_TRANSFER = 'bank_transfer',
}

class PaymentProcessor {
  processPayment(amount: number, type: PaymentType): boolean {
    switch (type) {
      case PaymentType.CREDIT_CARD:
        return this.processCreditCard(amount);
      case PaymentType.PAYPAL:
        return this.processPayPal(amount);
      case PaymentType.BANK_TRANSFER:
        return this.processBankTransfer(amount);
      default:
        return false;
    }
  }

  private processCreditCard(amount: number): boolean {
    console.log(`Processing credit card payment: $${amount}`);
    return true;
  }

  private processPayPal(amount: number): boolean {
    console.log(`Processing PayPal payment: $${amount}`);
    return true;
  }

  private processBankTransfer(amount: number): boolean {
    console.log(`Processing bank transfer: $${amount}`);
    return true;
  }

  // PROBLEM: Want to add cryptocurrency payment?
  // PROBLEM: Have to modify this class!
  // PROBLEM: Risk breaking existing payment methods!
}
