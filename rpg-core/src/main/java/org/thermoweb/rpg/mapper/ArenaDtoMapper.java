package org.thermoweb.rpg.mapper;

import org.thermoweb.core.data.Pair;
import org.thermoweb.rpg.dto.ArenaDto;
import org.thermoweb.rpg.environment.Arena;

import java.util.Map;
import java.util.stream.Collectors;

public class ArenaDtoMapper {
    public static ArenaDto map(Arena arena) {
        String[][] grid = new String[arena.getGridPattern().getX()][arena.getGridPattern().getY()];
        arena.getCharacterPairMap().forEach((characterId, integerIntegerPair) -> grid[integerIntegerPair.getLeft()][integerIntegerPair.getRight()] = characterId);
        return ArenaDto.builder()
                .characters(arena.getCharacters().stream().map(CharacterDtoMapper::map).toList())
                .gridpattern(arena.getGridPattern())
                //.grid(grid)
                .charactersPosition(arena.getCharacterPairMap())
                .build();
    }

    public static Arena map(ArenaDto arenaDto) {
        Map<String, Pair<Integer, Integer>> characterPairMap = arenaDto.charactersPosition()
                .entrySet()
                .stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue
                ));
        String[][] grid = new String[arenaDto.gridpattern().getX()][arenaDto.gridpattern().getY()];
        characterPairMap.forEach((characterId, integerIntegerPair) -> grid[integerIntegerPair.getLeft()][integerIntegerPair.getRight()] = characterId);
        return Arena.builder()
                .gridPattern(arenaDto.gridpattern())
                //.grid(grid)
                .characters(arenaDto.characters().stream().map(CharacterDtoMapper::map).toList())
                .characterPairMap(characterPairMap)
                .build();
    }
}
