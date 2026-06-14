package com.lld.oops.encapsulation;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Encapsulation: Bundling data and methods together with controlled access.
 */
public class BankAccount {
    private String accountNumber;
    private double balance;
    private List<Transaction> transactionHistory;
    private static final double MIN_BALANCE = 100.0;

    public BankAccount(String accountNumber, double initialBalance) {
        this.accountNumber = accountNumber;
        this.balance = initialBalance;
        this.transactionHistory = new ArrayList<>();
        recordTransaction("deposit", initialBalance);
    }

    public boolean deposit(double amount) {
        if (amount <= 0) {
            System.out.println("❌ Deposit amount must be positive");
            return false;
        }

        balance += amount;
        recordTransaction("deposit", amount);
        System.out.println("✅ Deposited $" + amount + ". New balance: $" + balance);
        return true;
    }

    public boolean withdraw(double amount) {
        if (amount <= 0) {
            System.out.println("❌ Withdrawal amount must be positive");
            return false;
        }

        if (balance - amount < MIN_BALANCE) {
            System.out.println("❌ Cannot withdraw $" + amount + ". Minimum balance: $" + MIN_BALANCE);
            return false;
        }

        balance -= amount;
        recordTransaction("withdrawal", amount);
        System.out.println("✅ Withdrew $" + amount + ". New balance: $" + balance);
        return true;
    }

    public double getBalance() {
        return balance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public List<Transaction> getTransactionHistory() {
        return new ArrayList<>(transactionHistory);  // Return copy to prevent modification
    }

    private void recordTransaction(String type, double amount) {
        transactionHistory.add(new Transaction(type, amount, LocalDateTime.now()));
    }

    // Inner class for transaction
    public static class Transaction {
        public final String type;
        public final double amount;
        public final LocalDateTime timestamp;

        public Transaction(String type, double amount, LocalDateTime timestamp) {
            this.type = type;
            this.amount = amount;
            this.timestamp = timestamp;
        }

        @Override
        public String toString() {
            return type.toUpperCase() + ": $" + amount + " - " + timestamp.toLocalTime();
        }
    }
}
