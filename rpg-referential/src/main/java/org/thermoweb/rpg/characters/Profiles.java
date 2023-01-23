package org.thermoweb.rpg.characters;

import org.thermoweb.rpg.equipment.Equipment;
import org.thermoweb.rpg.equipment.TorsoArmor;
import org.thermoweb.rpg.equipment.Weapon;

import java.util.Collections;
import java.util.List;
import java.util.Set;

public enum Profiles {
    FIGHTER(new Statistics(10, 5, -5), List.of(Weapon.SWORD)),
    HUNTER(new Statistics(5, 5, 0), List.of(Weapon.CROSSBOW)),
    ROGUE(new Statistics(5, 10, -5), List.of(Weapon.SWORD)),
    WIZARD(new Statistics(-5, 5, 10), List.of(Weapon.STAFF, TorsoArmor.APPRENTICE_ROBE), Skills.SPELL_CASTING);

    private final Statistics statistics;
    private final List<Equipment<?>> starterSet;
    private final Set<Skills> skills;

    Profiles(Statistics statistics, List<Equipment<?>> starterSet) {
        this(statistics, starterSet, Collections.emptySet());
    }

    Profiles(Statistics statistics, List<Equipment<?>> starterSet, Set<Skills> skills) {
        this.statistics = statistics;
        this.starterSet = starterSet;
        this.skills = skills;
    }

    Profiles(Statistics statistics, List<Equipment<?>> starterSet, Skills... skills) {
        this(statistics, starterSet, Set.of(skills));
    }

    public Statistics getStatistics() {
        return statistics;
    }

    public Set<Skills> getSkills() {
        return skills;
    }

    public List<Equipment<?>> getStarterSet() {
        return starterSet;
    }
}
