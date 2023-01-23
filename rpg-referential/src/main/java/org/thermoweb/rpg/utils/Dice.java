package org.thermoweb.rpg.utils;

public enum Dice {
    D4(4),
    D6(6),
    D8(8),
    D10(10),
    D12(12),
    D20(20),
    D100(100);

    private final int faces;

    Dice(int faces) {
        this.faces = faces;
    }

    public int getFaces() {
        return faces;
    }

    public int roll() {
        return DiceRoller.roll(faces);
    }
}
