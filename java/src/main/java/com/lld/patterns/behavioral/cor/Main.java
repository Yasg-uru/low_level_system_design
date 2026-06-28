package com.lld.patterns.behavioral.cor;

/**
 * Driver client demonstrating the Chain of Responsibility Pattern in Java.
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("=============================================================");
        System.out.println("=== Chain of Responsibility Design Pattern: Support Tiers ===");
        System.out.println("=============================================================\n");

        IHandler l1 = new Level1Support();
        IHandler l2 = new Level2Support();
        IHandler l3 = new Level3Support();

        // Establish Chain Relationships: L1 -> L2 -> L3
        l1.setNext(l2).setNext(l3);

        System.out.println("--- Executing Chain Pipeline ---");

        // Handled instantly by L1
        System.out.println(l1.handle("password reset"));

        // Passed past L1 -> Evaluated and captured by L2
        System.out.println(l1.handle("bug report"));

        // Passed past L1 and L2 -> Evaluated and captured by L3
        System.out.println(l1.handle("billing issue"));

        // Evaluated by all layers -> Falls out through base handler terminal fallback block
        System.out.println(l1.handle("server crash"));
    }
}
