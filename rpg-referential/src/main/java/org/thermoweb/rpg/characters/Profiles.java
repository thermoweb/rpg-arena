package org.thermoweb.rpg.characters;

public enum Profiles {
    FIGHTER(new Statistics(10, 5, -5)),
    HUNTER(new Statistics(5, 5, 0)),
    ROGUE(new Statistics(5, 10, -5)),
    WIZARD(new Statistics(-5, 5, 10));

    private final Statistics statistics;

    Profiles(Statistics statistics) {
        this.statistics = statistics;
    }

    public Statistics getStatistics() {
        return statistics;
    }
}
