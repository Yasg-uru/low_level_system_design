# Memento Pattern

## Overview

The **Memento Pattern** is a behavioral design pattern that permits capturing and externalizing an object's internal state without violating encapsulation, so that the object can be restored to this state later.

It is particularly useful for implementing mechanisms like:
- Undo/redo stacks.
- Checkpoints and draft autosaves.
- Rollback mechanisms for transactions.

## Structure

The Memento pattern involves three main actors:
1. **Originator** (`TextEditor`): The object whose state we want to track. It creates mementos containing snapshots of its state and uses them to restore itself.
2. **Memento** (`EditorMemento`): The immutable snapshot object that stores the state of the Originator.
3. **Caretaker** (`TextEditorHistory`): The manager that keeps track of the history stack of mementos but never modifies or inspects their state.

```
+---------------+           creates            +-----------------+
|  TextEditor   |----------------------------->|  EditorMemento  |
| (Originator)  |<-----------------------------|    (Memento)    |
+---------------+           restores           +-----------------+
        |                                               ^
        |                                               |
        | uses                                          | managed by
        v                                               |
+-------------------+                                   |
| TextEditorHistory |-----------------------------------+
|    (Caretaker)    |
+-------------------+
```

## Benefits

- **Encapsulation Safety**: The Memento pattern keeps the details of the Originator's state hidden from the rest of the application (including the Caretaker).
- **Simplified Originator**: By delegating history management to the Caretaker, the Originator remains focused on its primary responsibility (managing the active state and business logic).
- **Clean Undo History**: State changes can be recorded sequentially and traversed backward or forward.

## Example Scenario

In this implementation:
- **`TextEditor`** maintains a text content string.
- When `save()` is called, it creates a new **`EditorMemento`** containing the current content.
- The caretaker **`TextEditorHistory`** holds a stack of these mementos.
- When an undo action is requested, the caretaker pops the last memento and calls `restore(memento)` on the `TextEditor`.

## Common Mistakes

❌ **Bad approach (Breaking Encapsulation)**
Exposing mutable internal fields to allow external state tracking:
```java
public class TextEditor {
    public String content; // Violates encapsulation
}

public class History {
    private List<String> states = new ArrayList<>();
    
    public void save(TextEditor editor) {
        states.add(editor.content); // Directly accessing internal state
    }
}
```

✅ **Good approach (Memento Pattern)**
Encapsulating the snapshot creation and restoration:
```java
public class EditorMemento {
    private final String content;
    public EditorMemento(String content) { this.content = content; }
    public String getContent() { return content; }
}

public class TextEditor {
    private String content;

    public EditorMemento save() {
        return new EditorMemento(content);
    }

    public void restore(EditorMemento memento) {
        if (memento != null) {
            this.content = memento.getContent();
        }
    }
}
```

---

## Real-world Example: Game Save System (Subpackage)

For a more comprehensive real-world scenario (including coordinates, inventory list deep-copying, manual save slots, and rolling autosaves), refer to the files under the `game` subpackage:

- **Originator**: [GameState.java](file:///Users/apple/default/lld%20practice/java/src/main/java/com/lld/patterns/behavioral/memento/game/GameState.java)
- **Memento**: [GameMemento.java](file:///Users/apple/default/lld%20practice/java/src/main/java/com/lld/patterns/behavioral/memento/game/GameMemento.java)
- **Caretaker**: [SaveSlotManager.java](file:///Users/apple/default/lld%20practice/java/src/main/java/com/lld/patterns/behavioral/memento/game/SaveSlotManager.java)
- **Demo Runner**: [Main.java (Game)](file:///Users/apple/default/lld%20practice/java/src/main/java/com/lld/patterns/behavioral/memento/game/Main.java)

### Key Design Details (Java Implementation):
1. **Records**: Uses Java `record` classes (`Position` and `GameStateSnapshot`) to model immutable state representations cleanly.
2. **Deep Copying**: Ensures deep-copying of the position coordinate objects and collections (via `List.copyOf` / `new ArrayList<>`) inside `GameMemento` to protect the originator's state from outside mutation.
3. **Caretaker Logic**: Implements both a `Map` for manual save slots and an `ArrayList` acting as a bounded queue for checkpoint autosaves.

