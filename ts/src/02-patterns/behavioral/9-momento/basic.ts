/**
 * MEMENTO DESIGN PATTERN EXAMPLE
 * * Intent: Without violating encapsulation, capture and externalize an object's 
 * internal state so that the object can be restored to this state later.
 * * Key Components:
 * 1. Memento: The immutable snapshot object holding historical state data.
 * 2. Originator: The active object whose state we track (TextEditor).
 * 3. Caretaker: The manager holding onto the history stack (History).
 */

// --- 1. THE MEMENTO LAYER ---
// An immutable object containing the state snapshot. 
// It should strictly prevent external objects from mutating the saved state.
class EditorMemento {
  constructor(private readonly content: string) {}

  public getContent(): string {
    return this.content;
  }
}

// --- 2. THE ORIGINATOR ---
// The core object containing actual business data state that needs saving/restoring.
class TextEditor {
  constructor(private content: string) {}

  public write(content: string): void {
    this.content = content;
  }

  public read(): string {
    return this.content;
  }

  // Creates a snapshot wrapped inside a Memento object
  public save(): EditorMemento {
    return new EditorMemento(this.content);
  }

  // Restores the internal state from the passed Memento
  public restore(memento: EditorMemento): void {
    this.content = memento.getContent();
  }
}

// --- 3. THE CARETAKER ---
// Responsible for tracking and storing snapshots. It never updates or evaluates the content inside.
class TextEditorHistory {
  private mementos: EditorMemento[] = [];

  public push(memento: EditorMemento): void {
    this.mementos.push(memento);
  }

  public pop(): EditorMemento | undefined {
    return this.mementos.pop();
  }
}

// --- 4. DEMONSTRATION RUN ---

function runMementoDemo(): void {
  const editor = new TextEditor("");
  const history = new TextEditorHistory();

  console.log("--- Starting Text Mutations & Snapshot Captures ---");
  
  editor.write("Hello");
  history.push(editor.save()); // Save State 1

  editor.write("Hello, World");
  history.push(editor.save()); // Save State 2

  editor.write("Hello, World! (Latest Unsaved Draft)");
  console.log(`Current Editor View: "${editor.read()}"`);

  console.log("\n--- Triggering Undo Actions (Restoring State) ---");

  // Undo 1: Pop State 2
  const memento2 = history.pop();
  if (memento2) {
    editor.restore(memento2);
    console.log(`Undo 1 -> Current View: "${editor.read()}"`);
  }

  // Undo 2: Pop State 1
  const memento1 = history.pop();
  if (memento1) {
    editor.restore(memento1);
    console.log(`Undo 2 -> Current View: "${editor.read()}"`);
  }
}

runMementoDemo();