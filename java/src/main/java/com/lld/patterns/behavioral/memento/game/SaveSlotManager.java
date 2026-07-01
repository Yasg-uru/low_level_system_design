package com.lld.patterns.behavioral.memento.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SaveSlotManager {
    private final Map<Integer, GameMemento> slots = new HashMap<>();
    private final List<GameMemento> autoSaveHistory = new ArrayList<>();
    private static final int MAX_AUTOSAVES = 5;

    public void saveToSlot(int slotNumber, GameState gameState) {
        this.slots.put(slotNumber, gameState.save());
    }

    public void loadFromSlot(int slotNumber, GameState game) {
        GameMemento memento = this.slots.get(slotNumber);
        if (memento == null) {
            throw new IllegalArgumentException("Slot does not exist: " + slotNumber);
        }
        game.load(memento);
    }

    public void autoSave(GameState game) {
        this.autoSaveHistory.add(game.save());
        if (this.autoSaveHistory.size() > MAX_AUTOSAVES) {
            this.autoSaveHistory.remove(0);
        }
    }

    public void loadLastAutoSave(GameState game) {
        if (this.autoSaveHistory.isEmpty()) {
            throw new IllegalStateException("No autosave found");
        }
        GameMemento last = this.autoSaveHistory.get(this.autoSaveHistory.size() - 1);
        game.load(last);
    }
}
