package org.thermoweb.rpg.rest.client;

import lombok.Builder;
import org.thermoweb.rpg.arena.Grid;
import org.thermoweb.rpg.dto.brain.BrainDto;

@Builder
public record EncounterCreationRequest(String characterId, String opponentId, Grid grid, BrainDto brain,
                                       BrainDto opponentBrain) {
}
