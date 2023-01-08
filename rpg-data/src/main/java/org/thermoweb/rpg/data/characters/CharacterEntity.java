package org.thermoweb.rpg.data.characters;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.thermoweb.rpg.actions.Spells;
import org.thermoweb.rpg.characters.BrainType;
import org.thermoweb.rpg.characters.Profiles;
import org.thermoweb.rpg.characters.Species;
import org.thermoweb.rpg.equipment.slots.Slots;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@Builder
public class CharacterEntity {

    @Id
    private final String id;
    private final String name;
    private final Species species;
    private final Profiles profiles;
    private final BrainEntity brain;
    private final int maxHitPoints;
    private final int hitPoints;
    private final Statistics statistics;
    private Map<Slots, Equipment> equipment;
    private List<Spells> spellbook;

    @Builder
    @Getter
    @Setter
    public static class Statistics {
        private int force;
        private int dexterity;
        private int intelligence;
    }

    @Builder
    @Getter
    @Setter
    public static class Equipment {
        private String name;
        private int attrition;
    }

    @Builder
    @Getter
    @Setter
    public static class BrainEntity {
        private BrainType brainType;
        private String uri;
    }
}
