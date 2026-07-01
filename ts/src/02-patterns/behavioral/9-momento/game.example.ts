interface GameStateSnapshot {
    level: number;
    score: number;
    playerPosition: { x: number; y: number; };
    inventory: string[];
    health: number;
}
// momento
class GameMomento {
    private readonly snapshot: GameStateSnapshot;
    private readonly takenAt: Date;
    constructor(snapshot: GameStateSnapshot) {
        this.snapshot = Object.freeze({ ...snapshot, inventory: [...snapshot.inventory] });
        this.takenAt = new Date();
    }
    getSnapShot(): GameStateSnapshot {
        return { ...this.snapshot, inventory: [...this.snapshot.inventory] };
    }
    getTakenAt(): Date {
        return this.takenAt;
    }
}
// orginator 
class GameState {
    private level = 1;
    private score = 0;
    private position = { x: 0, y: 0 };
    private inventory: string[] = [];
    private health: number = 100;
    save(): GameMomento {
        return new GameMomento({
            level: this.level, score: this.score, playerPosition: { ...this.position }, inventory: [...this.inventory], health: this.health
        })
    }
    load(momento: GameMomento): void {
        const s = momento.getSnapShot();
        this.level = s.level;
        this.position = s.playerPosition;
        this.score = s.score;
        this.inventory = s.inventory;
        this.health = s.health
    }
    takeDamage(amount: number): void {
        this.health = Math.max(0, this.health - amount);
    }
}
//caretaker 
class SaveSlotManager {
    private slots = new Map<number, GameMomento>();
    private autoSaveHistory: GameMomento[] = [];
    private readonly MAX_AUTOSAVES = 5;
    saveToSlot(slotNumber: number, gameState: GameState): void {
        this.slots.set(slotNumber, gameState.save());
    }
    loadFromSlot(slotNumber: number, game: GameState) {
        const momento: GameMomento | undefined = this.slots.get(slotNumber);
        if (!momento) throw new Error('slot does not exists');
        game.load(momento);

    }
    autoSave(game: GameState): void {
        this.autoSaveHistory.push(game.save());
        if (this.autoSaveHistory.length > this.MAX_AUTOSAVES) {
            this.autoSaveHistory.shift();
        }
    }
    loadLastAutoSave(game: GameState): void {
        const last = this.autoSaveHistory[this.autoSaveHistory.length - 1];
        if (!last) throw Error("No autosave found");
        game.load(last)
    }

}
const game = new GameState();
const saves = new SaveSlotManager();
game.takeDamage(10);
saves.autoSave(game);
game.takeDamage(95);
saves.loadLastAutoSave(game);
