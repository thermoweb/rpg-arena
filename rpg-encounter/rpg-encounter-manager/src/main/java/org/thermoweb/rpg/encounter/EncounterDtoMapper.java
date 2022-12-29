package org.thermoweb.rpg.encounter;

import org.thermoweb.rpg.mapper.CharacterDtoMapper;
import org.thermoweb.rpg.characters.DefaultCharacter;
import org.thermoweb.rpg.dto.EncounterDto;
import org.thermoweb.rpg.encounter.states.EncounterState;
import org.thermoweb.rpg.environment.Arena;

import java.util.List;
import java.util.Optional;

public class EncounterDtoMapper {
    public static EncounterDto map(Encounter encounter) {
        return EncounterDto.builder()
                .id(encounter.getId())
                .status(Optional.ofNullable(encounter.getState()).map(EncounterState::getStatus).orElse(null))
                .characters(encounter.getCharacters().stream().map(CharacterDtoMapper::map).toList())
                .build();
    }

    public static Encounter map(EncounterDto encounterDto) {
        List<DefaultCharacter> characters = encounterDto.characters().stream().map(CharacterDtoMapper::map).toList();
        return Encounter.builder()
                .id(encounterDto.id())
                .characters(characters)
                .combatLog(CombatLog.builder().build())
                .arena(Arena.builder().gridPattern(encounterDto.grid()).characters(characters).build())
                .build();
    }
}
