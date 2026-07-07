package com.lld.patterns.behavioral.interpreter;

public class Main {
    public static void main(String[] args) {
        System.out.println("=============================================================");
        System.out.println("=== Interpreter Design Pattern: Rule Engine Demo (Java)   ===");
        System.out.println("=============================================================\n");

        IExpression highValueConfirmed = new AndExpression(
                new TotalGreaterThanExpression(1000),
                new StatusEqualsExpression("confirmed")
        );

        IExpression eligibleForDiscount = new OrExpression(
                highValueConfirmed,
                new TypeEqualsExpression("premium")
        );

        OrderContext order1 = new OrderContext("confirmed", 1500, "regular", "user1");
        OrderContext order2 = new OrderContext("pending", 500, "premium", "user2");
        OrderContext order3 = new OrderContext("pending", 200, "regular", "user3");

        System.out.println("--- Rule Engine Evaluation ---");
        System.out.println("Order 1 eligible for discount: " + eligibleForDiscount.interpret(order1));
        System.out.println("Order 2 eligible for discount: " + eligibleForDiscount.interpret(order2));
        System.out.println("Order 3 eligible for discount: " + eligibleForDiscount.interpret(order3));
    }
}