package org.thermoweb.rpg.rest.mapper;

import org.thermoweb.rpg.data.encounters.EncounterEntity;
import org.thermoweb.rpg.dto.EncounterDto;

public class EncounterMapper {

    public static EncounterDto map(EncounterEntity encounterEntity) {
        return EncounterDto.builder().id(encounterEntity.getId()).build();
    }

    public EncounterEntity map(EncounterDto encounterDto) {
        return EncounterEntity.builder().id(encounterDto.id()).build();
    }
}
