package com.lld.patterns.structural.proxy.logging;

public interface LoggingBankAccountInterface {
    void deposit(double amount);
    void withdraw(double amount) throws Exception;
    double getBalance();
}
