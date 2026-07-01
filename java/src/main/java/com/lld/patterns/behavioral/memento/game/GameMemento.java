package com.lld.patterns.behavioral.memento.game;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GameMemento {
    private final GameStateSnapshot snapshot;
    private final Date takenAt;

    public GameMemento(GameStateSnapshot snapshot) {
        // Deep copy the position and inventory to preserve immutability
        Position copiedPosition = new Position(snapshot.playerPosition().x(), snapshot.playerPosition().y());
        List<String> copiedInventory = new ArrayList<>(snapshot.inventory());
        this.snapshot = new GameStateSnapshot(
            snapshot.level(),
            snapshot.score(),
            copiedPosition,
            List.copyOf(copiedInventory), // Creates an unmodifiable list
            snapshot.health()
        );
        this.takenAt = new Date();
    }

    public GameStateSnapshot getSnapshot() {
        // Return a snapshot with safe copies of mutable elements
        Position copiedPosition = new Position(snapshot.playerPosition().x(), snapshot.playerPosition().y());
        List<String> copiedInventory = new ArrayList<>(snapshot.inventory());
        return new GameStateSnapshot(
            snapshot.level(),
            snapshot.score(),
            copiedPosition,
            List.copyOf(copiedInventory),
            snapshot.health()
        );
    }

    public Date getTakenAt() {
        return new Date(takenAt.getTime());
    }
}
