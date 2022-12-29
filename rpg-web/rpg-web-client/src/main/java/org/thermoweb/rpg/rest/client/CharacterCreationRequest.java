package org.thermoweb.rpg.rest.client;

import lombok.Builder;
import org.thermoweb.rpg.characters.Profiles;
import org.thermoweb.rpg.characters.Species;
import org.thermoweb.rpg.characters.Statistics;
import org.thermoweb.rpg.dto.CharacterDto;
import org.thermoweb.rpg.equipment.slots.Slots;

import java.util.Map;

@Builder
public record CharacterCreationRequest(String name,
                                       Map<Slots, CharacterDto.Equipment> equipment,
                                       Statistics.StartStatistics statistics,
                                       Species species,
                                       Profiles profile) {

}
