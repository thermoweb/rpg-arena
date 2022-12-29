package org.thermoweb.rpg.utils;

import java.util.random.RandomGenerator;

public class DiceRoller {

    private static final RandomGenerator generator = RandomGenerator.getDefault();

    private DiceRoller() {
    }

    public static int roll(int number) {
        return generator.nextInt(number);
    }
}
