package org.thermoweb.rpg.encounter.client;

import lombok.Builder;
import org.thermoweb.rpg.arena.Grid;
import org.thermoweb.rpg.dto.CharacterDto;

import java.util.List;

@Builder
public record EncounterManagerCreationRequest(List<CharacterDto> participants, Grid grid) {
}
