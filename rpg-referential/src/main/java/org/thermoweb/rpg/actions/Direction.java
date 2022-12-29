package org.thermoweb.rpg.actions;

import java.util.List;
import java.util.random.RandomGenerator;

public enum Direction {
    UP,
    DOWN,
    LEFT,
    RIGHT;

    private static final List<Direction> VALUES = List.of(values());
    private static final int SIZE = VALUES.size();
    private static final RandomGenerator generator = RandomGenerator.getDefault();

    public static Direction getRandomDirection() {
        return VALUES.get(generator.nextInt(SIZE));
    }
}
