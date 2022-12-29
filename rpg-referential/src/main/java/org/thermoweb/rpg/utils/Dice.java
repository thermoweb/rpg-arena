package org.thermoweb.rpg.utils;

public enum Dice {
    D4(4),
    D6(6),
    D6_2(6, 2),
    D8(8),
    D10(10),
    D12(12),
    D20(20),
    D100(100);

    private final int faces;
    private final int number;

    Dice(int faces) {
        this.faces = faces;
        this.number = 1;
    }

    Dice(int faces, int number) {
        this.faces = faces;
        this.number = number;
    }

    public int roll() {
        int result = 0;
        for (int i = 0; i < number; i++) {
            result += DiceRoller.roll(faces);
        }
        return result;
    }
}
