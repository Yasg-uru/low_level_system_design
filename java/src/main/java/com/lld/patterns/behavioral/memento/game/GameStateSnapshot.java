package com.lld.patterns.behavioral.memento.game;

import java.util.List;

public record GameStateSnapshot(
    int level,
    int score,
    Position playerPosition,
    List<String> inventory,
    int health
) {}
