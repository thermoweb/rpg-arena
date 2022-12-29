package org.thermoweb.rpg.dto;

import lombok.Builder;
import org.thermoweb.rpg.arena.Grid;
import org.thermoweb.rpg.encounters.EncounterStatus;

import java.util.List;

@Builder
public record EncounterDto(String id, EncounterStatus status, List<CharacterDto> characters, Grid grid) {
}