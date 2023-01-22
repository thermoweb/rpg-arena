package org.thermoweb.rpg.characters;

import java.util.Collections;
import java.util.Set;

public enum Profiles {
    FIGHTER(new Statistics(10, 5, -5)),
    HUNTER(new Statistics(5, 5, 0)),
    ROGUE(new Statistics(5, 10, -5)),
    WIZARD(new Statistics(-5, 5, 10), Skills.SPELL_CASTING);

    private final Statistics statistics;
    private final Set<Skills> skills;

    Profiles(Statistics statistics) {
        this.statistics = statistics;
        skills = Collections.emptySet();
    }

    Profiles(Statistics statistics, Skills... skills) {
        this.statistics = statistics;
        this.skills = Set.of(skills);
    }

    public Statistics getStatistics() {
        return statistics;
    }

    public Set<Skills> getSkills() {
        return skills;
    }
}
