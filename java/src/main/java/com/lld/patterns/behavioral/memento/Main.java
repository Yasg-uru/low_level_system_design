package com.lld.patterns.behavioral.memento;

/**
 * Main demonstration class for the Memento pattern.
 */
public class Main {
    public static void main(String[] args) {
        TextEditor editor = new TextEditor("");
        TextEditorHistory history = new TextEditorHistory();

        System.out.println("--- Starting Text Mutations & Snapshot Captures ---");

        editor.write("Hello");
        history.push(editor.save()); // Save State 1

        editor.write("Hello, World");
        history.push(editor.save()); // Save State 2

        editor.write("Hello, World! (Latest Unsaved Draft)");
        System.out.println("Current Editor View: \"" + editor.read() + "\"");

        System.out.println("\n--- Triggering Undo Actions (Restoring State) ---");

        // Undo 1: Pop State 2
        EditorMemento memento2 = history.pop();
        if (memento2 != null) {
            editor.restore(memento2);
            System.out.println("Undo 1 -> Current View: \"" + editor.read() + "\"");
        }

        // Undo 2: Pop State 1
        EditorMemento memento1 = history.pop();
        if (memento1 != null) {
            editor.restore(memento1);
            System.out.println("Undo 2 -> Current View: \"" + editor.read() + "\"");
        }
    }
}
