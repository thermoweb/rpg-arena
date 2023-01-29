package org.thermoweb.rpg.characters;

import lombok.Getter;

@Getter
public enum Skills {
    SPELL_CASTING("Spell casting", "The character can cast spells.");

    private final String shortName;
    private final String description;
    Skills(String shortName, String description) {
        this.shortName = shortName;
        this.description = description;
    }
}
