/**
 * Encapsulation: Bundling data and methods together
 *
 * Controls access to internal state and ensures data integrity
 * through controlled public interfaces.
 */

class BankAccount {
  private accountNumber: string;
  private balance: number;
  private transactionHistory: Array<{ type: string; amount: number; date: Date }> = [];
  private readonly minBalance: number = 100;

  constructor(accountNumber: string, initialBalance: number) {
    this.accountNumber = accountNumber;
    this.balance = initialBalance;
    this.recordTransaction('deposit', initialBalance);
  }

  deposit(amount: number): boolean {
    if (amount <= 0) {
      console.log('❌ Deposit amount must be positive');
      return false;
    }

    this.balance += amount;
    this.recordTransaction('deposit', amount);
    console.log(`✅ Deposited $${amount}. New balance: $${this.balance}`);
    return true;
  }

  withdraw(amount: number): boolean {
    if (amount <= 0) {
      console.log('❌ Withdrawal amount must be positive');
      return false;
    }

    if (this.balance - amount < this.minBalance) {
      console.log(`❌ Cannot withdraw $${amount}. Minimum balance: $${this.minBalance}`);
      return false;
    }

    this.balance -= amount;
    this.recordTransaction('withdrawal', amount);
    console.log(`✅ Withdrew $${amount}. New balance: $${this.balance}`);
    return true;
  }

  getBalance(): number {
    return this.balance;
  }

  getAccountNumber(): string {
    return this.accountNumber;
  }

  getTransactionHistory(): Array<{ type: string; amount: number; date: Date }> {
    return [...this.transactionHistory];
  }

  private recordTransaction(type: string, amount: number): void {
    this.transactionHistory.push({
      type,
      amount,
      date: new Date(),
    });
  }
}

// Usage
const account = new BankAccount('ACC-12345', 1000);
console.log(`Initial balance: $${account.getBalance()}`);

account.deposit(500);
account.withdraw(300);
account.withdraw(700); // Should fail - exceeds minimum

// Cannot access private fields directly (TypeScript error at compile time)
// account.balance = -1000; // Error!

console.log(`\nFinal balance: $${account.getBalance()}`);
console.log(`\nTransaction history:`);
account.getTransactionHistory().forEach((t) => {
  console.log(`  ${t.type.toUpperCase()}: $${t.amount} - ${t.date.toLocaleTimeString()}`);
});
