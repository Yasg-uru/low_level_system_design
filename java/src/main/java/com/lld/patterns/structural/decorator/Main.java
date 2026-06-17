package com.lld.patterns.structural.decorator;

/**
 * Driver class demonstrating dynamic object decoration.
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("=== Coffee Decorator Demonstration ===");

        // Simple Coffee
        Coffee simpleCoffee = new SimpleCoffee();
        System.out.println("Cost: " + simpleCoffee.getCost()); // 5
        System.out.println("Description: " + simpleCoffee.getDescription()); // Simple coffee
        System.out.println();

        // Milk Coffee
        Coffee milkCoffee = new MilkDecorator(new SimpleCoffee());
        System.out.println("Cost: " + milkCoffee.getCost()); // 7
        System.out.println("Description: " + milkCoffee.getDescription()); // Simple coffee, milk
        System.out.println();

        // Sugar Milk Coffee
        Coffee sugarMilkCoffee = new SugarDecorator(new MilkDecorator(new SimpleCoffee()));
        System.out.println("Cost: " + sugarMilkCoffee.getCost()); // 8
        System.out.println("Description: " + sugarMilkCoffee.getDescription()); // Simple coffee, milk, sugar
        System.out.println();

        // Whipped Cream + Sugar + Milk Coffee
        Coffee premiumCoffee = new WhippedCreamDecorator(
            new SugarDecorator(
                new MilkDecorator(
                    new SimpleCoffee()
                )
            )
        );
        System.out.println("Cost: " + premiumCoffee.getCost()); // 11
        System.out.println("Description: " + premiumCoffee.getDescription()); // Simple coffee, milk, sugar, whipped cream
    }
}
