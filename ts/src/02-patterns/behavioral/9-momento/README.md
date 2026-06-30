# Memento Design Pattern

## What is it?

The **Memento Pattern** is a behavioral design pattern that allows capturing and externalizing an object's internal state without violating encapsulation, so that the object can be restored to this state later.

It is commonly used to implement undo/redo mechanisms, checkpoints, or transaction rollbacks in applications.

## Why use it?

- **Preserves Encapsulation**: Unlike exposing internal fields to save state (which violates encapsulation), the Memento pattern delegates state capture and restoration to the owner of the state (the Originator) itself.
- **Simplifies the Originator**: The Originator doesn't need to manage history or keep track of multiple versions of its state; that responsibility is delegated to the Caretaker.
- **Provides a Clean Undo/Redo mechanism**: It allows storing snapshots of the state sequentially and reverting to a specific point in time easily.

## When to use?

- When you need to provide an undo/redo feature (e.g., text editors, drawing applications, database transactions).
- When exposing the direct internal state/implementation details of an object would break its encapsulation.

## Structure

The pattern consists of three primary components:

1. **Originator**: The object that has some internal state we want to save. It creates the Memento containing a snapshot of its current state, and uses the Memento to restore its state.
2. **Memento**: A value object that stores a snapshot of the Originator's state. It is immutable and should not allow external access/mutation to the state other than by the Originator itself.
3. **Caretaker**: The object responsible for keeping track of the Mementos (e.g., a history stack). It does not inspect or operate on the contents of the Memento.

## Common Mistakes

❌ **Bad approach (Violates Encapsulation)**
Exposing internal private variables so that a history manager can save/restore them directly.
```typescript
class TextEditor {
  public content: string = ""; // Violation: Publicly mutable content
}

class History {
  private states: string[] = [];
  
  // Caretaker is responsible for managing the originator's internal structure
  public save(editor: TextEditor) {
    this.states.push(editor.content);
  }
}
```

✅ **Good approach (Memento Pattern)**
Encapsulating the snapshot generation and restoration inside the originator.
```typescript
class EditorMemento {
  constructor(private readonly content: string) {}
  public getContent() { return this.content; }
}

class TextEditor {
  private content: string = "";

  public save(): EditorMemento {
    return new EditorMemento(this.content);
  }

  public restore(memento: EditorMemento) {
    this.content = memento.getContent();
  }
}
```

## Key Points

- **Immutability**: Memento objects must be immutable to ensure history cannot be tampered with.
- **Single Responsibility Principle**: The Caretaker manages the stack of history, whereas the Originator manages its own active state.
