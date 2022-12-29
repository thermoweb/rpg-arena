package org.thermoweb.rpg.arena;

public enum Grid {
    SQUARE_8(8, 8),
    SQUARE_16(16, 16),
    SQUARE_32(32, 32);

    private final int x;
    private final int y;

    Grid(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
