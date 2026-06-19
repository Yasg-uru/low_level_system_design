// Logging Proxy Example - Records every call for monitoring/debugging

// Subject Interface
interface LoggingBankAccountInterface {
  deposit(amount: number): void;
  withdraw(amount: number): void;
  getBalance(): number;
}

// Real Subject - Contains business logic
class RealBankAccount implements LoggingBankAccountInterface {
  private balance: number = 0;

  deposit(amount: number): void {
    this.balance += amount;
  }

  withdraw(amount: number): void {
    if (amount > this.balance) {
      throw new Error("Insufficient funds");
    }
    this.balance -= amount;
  }

  getBalance(): number {
    return this.balance;
  }
}

// Logging Proxy - Records all method calls
class LoggingBankAccountProxy implements LoggingBankAccountInterface {
  private realAccount: RealBankAccount;
  private log: string[] = [];

  constructor(realAccount: RealBankAccount) {
    this.realAccount = realAccount;
  }

  private logCall(method: string, args: any[], result?: any, error?: Error): void {
    const timestamp = new Date().toISOString();
    const logEntry = {
      timestamp,
      method,
      arguments: args,
      result: error ? undefined : result,
      error: error ? error.message : undefined,
      success: !error
    };
    
    this.log.push(JSON.stringify(logEntry));
    console.log(`[LOG] ${timestamp} - ${method}(${args.map(arg => JSON.stringify(arg)).join(', ')})`);
    
    if (error) {
      console.log(`[LOG] Error: ${error.message}`);
    } else {
      console.log(`[LOG] Result: ${JSON.stringify(result)}`);
    }
  }

  deposit(amount: number): void {
    try {
      console.log(`\n--- Calling deposit(${amount}) ---`);
      this.realAccount.deposit(amount);
      this.logCall('deposit', [amount], 'Deposit successful');
    } catch (error) {
      this.logCall('deposit', [amount], undefined, error as Error);
      throw error;
    }
  }

  withdraw(amount: number): void {
    try {
      console.log(`\n--- Calling withdraw(${amount}) ---`);
      this.realAccount.withdraw(amount);
      this.logCall('withdraw', [amount], 'Withdrawal successful');
    } catch (error) {
      this.logCall('withdraw', [amount], undefined, error as Error);
      throw error;
    }
  }

  getBalance(): number {
    try {
      console.log(`\n--- Calling getBalance() ---`);
      const balance = this.realAccount.getBalance();
      this.logCall('getBalance', [], balance);
      return balance;
    } catch (error) {
      this.logCall('getBalance', [], undefined, error as Error);
      throw error;
    }
  }

  getLog(): string[] {
    return this.log;
  }

  printLog(): void {
    console.log("\n=== Operation Log ===");
    this.log.forEach((entry, index) => {
      console.log(`${index + 1}. ${entry}`);
    });
  }
}

// Usage
function loggingProxyDemo(): void {
  console.log("=== Logging Proxy Demo ===\n");

  const realAccount = new RealBankAccount();
  const loggingAccount = new LoggingBankAccountProxy(realAccount);

  console.log("Performing banking operations:");
  loggingAccount.deposit(1000);
  loggingAccount.deposit(500);
  loggingAccount.withdraw(200);
  loggingAccount.getBalance();

  console.log("\nAttempting invalid withdrawal:");
  try {
    loggingAccount.withdraw(2000);
  } catch (error) {
    console.log("Caught expected error:", (error as Error).message);
  }

  console.log("\nFinal balance:");
  loggingAccount.getBalance();

  console.log("\n");
  loggingAccount.printLog();
}

// Run the demo
loggingProxyDemo();
