package com.lld.patterns.behavioral.memento;

/**
 * The Memento class.
 * Holds the immutable state snapshot of the Editor.
 */
public class EditorMemento {
    private final String content;

    public EditorMemento(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }
}
