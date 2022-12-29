package org.thermoweb.rpg.dto;

import lombok.Builder;
import org.thermoweb.core.data.Pair;
import org.thermoweb.rpg.arena.Grid;

import java.util.List;
import java.util.Map;

@Builder
public record ArenaDto(List<CharacterDto> characters,
                       Grid gridpattern,
                       Map<String, Pair<Integer, Integer>> charactersPosition) {
}
