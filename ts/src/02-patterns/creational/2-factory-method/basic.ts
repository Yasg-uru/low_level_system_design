/**
 * Factory Method Pattern: Basic Implementation
 *
 * Creates objects without specifying exact classes.
 * Subclasses decide which class to instantiate.
 */

interface IButton {
  render(): void;
  onClick(): void;
}

class WindowsButton implements IButton {
  render(): void {
    console.log('Rendering Windows button');
  }

  onClick(): void {
    console.log('Windows button clicked');
  }
}

class MacOSButton implements IButton {
  render(): void {
    console.log('Rendering macOS button');
  }

  onClick(): void {
    console.log('macOS button clicked');
  }
}

// Creator (Abstract)
abstract class Dialog {
  abstract createButton(): IButton;

  render(): void {
    const button = this.createButton();
    button.render();
  }
}

// Concrete Creators
class WindowsDialog extends Dialog {
  createButton(): IButton {
    return new WindowsButton();
  }
}

class MacOSDialog extends Dialog {
  createButton(): IButton {
    return new MacOSButton();
  }
}

// Usage
const windowsDialog = new WindowsDialog();
windowsDialog.render(); // Creates and renders Windows button

const macDialog = new MacOSDialog();
macDialog.render(); // Creates and renders macOS button
