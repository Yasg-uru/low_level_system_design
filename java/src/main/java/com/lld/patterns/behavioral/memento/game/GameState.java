package com.lld.patterns.behavioral.memento.game;

import java.util.ArrayList;
import java.util.List;

public class GameState {
    private int level = 1;
    private int score = 0;
    private Position position = new Position(0, 0);
    private List<String> inventory = new ArrayList<>();
    private int health = 100;

    public GameMemento save() {
        return new GameMemento(new GameStateSnapshot(
            level,
            score,
            position,
            new ArrayList<>(inventory),
            health
        ));
    }

    public void load(GameMemento memento) {
        GameStateSnapshot s = memento.getSnapshot();
        this.level = s.level();
        this.position = s.playerPosition();
        this.score = s.score();
        this.inventory = new ArrayList<>(s.inventory());
        this.health = s.health();
    }

    public void takeDamage(int amount) {
        this.health = Math.max(0, this.health - amount);
    }

    // Getters and setters for demonstration/testing
    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public List<String> getInventory() {
        return new ArrayList<>(inventory);
    }

    public void addToInventory(String item) {
        this.inventory.add(item);
    }

    public int getHealth() {
        return health;
    }

    @Override
    public String toString() {
        return "GameState{" +
                "level=" + level +
                ", score=" + score +
                ", position=" + position +
                ", inventory=" + inventory +
                ", health=" + health +
                '}';
    }
}
