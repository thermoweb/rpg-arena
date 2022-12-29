package org.thermoweb.rpg.characters;

public enum Species {
    DWARF(new Statistics(10, -5, 0), 5),
    ELF(new Statistics(-5, 5, 5), 7),
    GNOME(new Statistics(-5, 0, 10), 5),
    HUMAN(new Statistics(5, 0, 0), 6);

    private final Statistics statistics;
    private final int speed;

    Species(Statistics statistics, int speed) {
        this.statistics = statistics;
        this.speed = speed;
    }

    public Statistics getStatistics() {
        return statistics;
    }

    public int getSpeed() {
        return speed;
    }
}
