package com.lld.patterns.behavioral.memento.game;

public class Main {
    public static void main(String[] args) {
        System.out.println("--- Starting Game Memento Demo ---");
        GameState game = new GameState();
        SaveSlotManager saves = new SaveSlotManager();

        game.addToInventory("Sword");
        game.addToInventory("Shield");
        game.setPosition(new Position(10, 20));
        game.setScore(150);
        
        System.out.println("Initial Game State: " + game);

        // Auto save before entering a dangerous boss fight
        System.out.println("\n[Autosaving game state...]");
        saves.autoSave(game);

        // Player takes huge damage
        System.out.println("\nPlayer fights boss...");
        game.takeDamage(90);
        game.setPosition(new Position(15, 25));
        game.addToInventory("Broken Armor");
        System.out.println("State after boss damage: " + game);

        // Save progress to Manual Save Slot 1
        System.out.println("\n[Saving progress manually to Slot 1...]");
        saves.saveToSlot(1, game);

        // Player dies/takes fatal damage
        System.out.println("\nPlayer takes fatal damage...");
        game.takeDamage(15);
        System.out.println("State after death: " + game);

        // Restore to autosave
        System.out.println("\n[Restoring to last autosave...]");
        saves.loadLastAutoSave(game);
        System.out.println("Restored state: " + game);

        // Restore to manual save slot 1
        System.out.println("\n[Restoring to manual save Slot 1...]");
        saves.loadFromSlot(1, game);
        System.out.println("Restored state from Slot 1: " + game);
    }
}
