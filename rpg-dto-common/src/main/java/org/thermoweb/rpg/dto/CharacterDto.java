package org.thermoweb.rpg.dto;

import lombok.Builder;
import org.thermoweb.rpg.actions.Spells;
import org.thermoweb.rpg.characters.Ability;
import org.thermoweb.rpg.characters.Profiles;
import org.thermoweb.rpg.characters.Species;
import org.thermoweb.rpg.dto.brain.BrainDto;
import org.thermoweb.rpg.equipment.slots.Slots;

import java.util.List;
import java.util.Map;

@Builder(toBuilder = true)
public record CharacterDto(String id,
                           String name,
                           Species species,
                           Profiles profiles,
                           BrainDto brain,
                           Map<Slots, Equipment> equipment,
                           Map<Ability, Integer> statistics,
                           List<Spells> spellbook,
                           int hitPoints,
                           int maxHitPoints) {

    @Builder
    public record Equipment(String name, int attrition) {

    }
}
