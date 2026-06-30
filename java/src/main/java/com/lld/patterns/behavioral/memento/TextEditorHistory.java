package com.lld.patterns.behavioral.memento;

import java.util.Stack;

/**
 * The Caretaker class.
 * Manages the history stack of mementos.
 */
public class TextEditorHistory {
    private final Stack<EditorMemento> mementos = new Stack<>();

    public void push(EditorMemento memento) {
        mementos.push(memento);
    }

    public EditorMemento pop() {
        if (mementos.isEmpty()) {
            return null;
        }
        return mementos.pop();
    }
}
