package com.lld.patterns.structural.proxy.logging;

public class RealBankAccount implements LoggingBankAccountInterface {
    private double balance = 0;

    @Override
    public void deposit(double amount) {
        this.balance += amount;
    }

    @Override
    public void withdraw(double amount) throws Exception {
        if (amount > this.balance) {
            throw new Exception("Insufficient funds");
        }
        this.balance -= amount;
    }

    @Override
    public double getBalance() {
        return this.balance;
    }
}
