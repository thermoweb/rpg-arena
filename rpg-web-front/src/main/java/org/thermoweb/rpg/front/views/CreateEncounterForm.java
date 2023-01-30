package org.thermoweb.rpg.front.views;

import lombok.Builder;
import org.thermoweb.rpg.arena.Grid;
import org.thermoweb.rpg.characters.BrainType;

@Builder
public record CreateEncounterForm(String characterId, String opponentId, Grid grid, BrainType brainType,
                                  String brainUri, BrainType opponentBrainType, String opponentBrainUri) {
}
