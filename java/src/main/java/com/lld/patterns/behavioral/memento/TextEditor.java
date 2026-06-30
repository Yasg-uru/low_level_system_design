package com.lld.patterns.behavioral.memento;

/**
 * The Originator class.
 * The object whose state needs to be saved and restored.
 */
public class TextEditor {
    private String content;

    public TextEditor(String content) {
        this.content = content;
    }

    public void write(String content) {
        this.content = content;
    }

    public String read() {
        return content;
    }

    /**
     * Creates a snapshot of the current state.
     */
    public EditorMemento save() {
        return new EditorMemento(content);
    }

    /**
     * Restores state from the memento.
     */
    public void restore(EditorMemento memento) {
        if (memento != null) {
            this.content = memento.getContent();
        }
    }
}
