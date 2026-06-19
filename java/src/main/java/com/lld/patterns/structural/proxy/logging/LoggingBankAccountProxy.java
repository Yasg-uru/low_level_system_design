package com.lld.patterns.structural.proxy.logging;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class LoggingBankAccountProxy implements LoggingBankAccountInterface {
    private final RealBankAccount realAccount;
    private final List<String> log;

    public LoggingBankAccountProxy(RealBankAccount realAccount) {
        this.realAccount = realAccount;
        this.log = new ArrayList<>();
    }

    private void logCall(String method, double arg, Object result, Exception error) {
        String timestamp = Instant.now().toString();
        StringBuilder logEntry = new StringBuilder();
        logEntry.append("{")
                .append("\"timestamp\":\"").append(timestamp).append("\",")
                .append("\"method\":\"").append(method).append("\",")
                .append("\"argument\":").append(arg).append(",");
        
        if (error != null) {
            logEntry.append("\"error\":\"").append(error.getMessage()).append("\",")
                    .append("\"success\":false");
        } else {
            logEntry.append("\"result\":\"").append(result).append("\",")
                    .append("\"success\":true");
        }
        logEntry.append("}");

        this.log.add(logEntry.toString());
        System.out.print("[LOG] " + timestamp + " - " + method + "(" + arg + ")");
        if (error != null) {
            System.out.println(" -> Error: " + error.getMessage());
        } else {
            System.out.println(" -> Result: " + result);
        }
    }

    @Override
    public void deposit(double amount) {
        try {
            System.out.println("\n--- Calling deposit(" + amount + ") ---");
            this.realAccount.deposit(amount);
            logCall("deposit", amount, "Deposit successful", null);
        } catch (Exception error) {
            logCall("deposit", amount, null, error);
            throw error;
        }
    }

    @Override
    public void withdraw(double amount) throws Exception {
        try {
            System.out.println("\n--- Calling withdraw(" + amount + ") ---");
            this.realAccount.withdraw(amount);
            logCall("withdraw", amount, "Withdrawal successful", null);
        } catch (Exception error) {
            logCall("withdraw", amount, null, error);
            throw error;
        }
    }

    @Override
    public double getBalance() {
        try {
            System.out.println("\n--- Calling getBalance() ---");
            double balance = this.realAccount.getBalance();
            logCall("getBalance", 0, balance, null);
            return balance;
        } catch (Exception error) {
            logCall("getBalance", 0, null, error);
            throw error;
        }
    }

    public List<String> getLog() {
        return this.log;
    }

    public void printLog() {
        System.out.println("\n=== Operation Log ===");
        for (int i = 0; i < log.size(); i++) {
            System.out.println((i + 1) + ". " + log.get(i));
        }
    }
}
