/**
 * MEDIATOR DESIGN PATTERN EXAMPLE
 * * Intent: Define an object that encapsulates how a set of objects interact. 
 * Mediator promotes loose coupling by keeping objects from referring to each 
 * other explicitly, and it lets you vary their interaction independently.
 */

// --- 1. THE MEDIATOR INTERFACE ---
interface IChatMediator {
  sendMessage(message: string, from: ChatUser): void;
  addUser(user: ChatUser): void;
}

// --- 2. THE CONCRETE MEDIATOR ---
class ChatMediator implements IChatMediator {
  private users: ChatUser[] = [];

  public addUser(user: ChatUser): void {
    if (!this.users.includes(user)) {
      this.users.push(user);
    }
  }

  public sendMessage(message: string, from: ChatUser): void {
    // Broadcast message to everyone EXCEPT the sender
    this.users
      .filter((user) => user !== from)
      .forEach((user) => user.receive(message, from.name));
  }
}

// --- 3. THE COLLEAGUE CLASS ---
class ChatUser {
  // Marking 'name' as public allows the mediator to read it for message routing
  constructor(public name: string, private mediator: IChatMediator) {}

  public send(message: string): void {
    console.log(`\n[${this.name}] sending: "${message}"`);
    this.mediator.sendMessage(message, this);
  }

  public receive(message: string, from: string): void {
    console.log(`   --> [${this.name}] received from [${from}]: "${message}"`);
  }
}

// --- 4. DEMONSTRATION RUN ---

function runMediatorDemo(): void {
  // Create the central hub (Mediator)
  const chatRoom = new ChatMediator();

  // Create participants (Colleagues) associated with the mediator
  const user1 = new ChatUser("Yash", chatRoom);
  const user2 = new ChatUser("Rohan", chatRoom);
  const user3 = new ChatUser("Ankit", chatRoom);

  // Register participants to the chat room
  chatRoom.addUser(user1);
  chatRoom.addUser(user2);
  chatRoom.addUser(user3);

  // Users communicate exclusively via the mediator, never directly with each other
  user1.send("Hey everyone, how are you?");
  user2.send("Doing great! Working on design patterns.");
}

runMediatorDemo();