package org.thermoweb.rpg.rest.client;

import org.thermoweb.rpg.arena.Grid;
import org.thermoweb.rpg.dto.brain.BrainDto;

public record EncounterCreationRequest(String characterId, Grid grid, BrainDto brain) {
}
