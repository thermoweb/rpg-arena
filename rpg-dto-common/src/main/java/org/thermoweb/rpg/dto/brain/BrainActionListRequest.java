package org.thermoweb.rpg.dto.brain;

import org.thermoweb.rpg.dto.ArenaDto;
import org.thermoweb.rpg.dto.CharacterDto;

public record BrainActionListRequest(CharacterDto character, ArenaDto arenaDto) {
}
