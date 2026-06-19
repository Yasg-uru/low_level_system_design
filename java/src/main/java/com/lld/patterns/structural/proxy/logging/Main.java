package com.lld.patterns.structural.proxy.logging;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Logging Proxy Demo ===");
        try {
            RealBankAccount realAccount = new RealBankAccount();
            LoggingBankAccountProxy loggingAccount = new LoggingBankAccountProxy(realAccount);

            System.out.println("Performing banking operations:");
            loggingAccount.deposit(1000);
            loggingAccount.deposit(500);
            loggingAccount.withdraw(200);
            loggingAccount.getBalance();

            System.out.println("\nAttempting invalid withdrawal:");
            try {
                loggingAccount.withdraw(2000);
            } catch (Exception error) {
                System.out.println("Caught expected error: " + error.getMessage());
            }

            System.out.println("\nFinal balance:");
            loggingAccount.getBalance();

            loggingAccount.printLog();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
