package org.thermoweb.rpg.environment;

import lombok.Getter;
import lombok.Setter;
import org.thermoweb.core.data.Pair;
import org.thermoweb.rpg.arena.Grid;
import org.thermoweb.rpg.characters.DefaultCharacter;
import org.thermoweb.rpg.utils.GridUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class Arena {
    private final List<DefaultCharacter> characters;
    private final DefaultCharacter[][] grid;
    private final Grid gridPattern;
    private final Map<String, Pair<Integer, Integer>> characterPairMap;

    public Arena(List<DefaultCharacter> characters, Grid grid) {
        this.characters = characters;
        this.gridPattern = grid;
        this.grid = new DefaultCharacter[grid.getX()][grid.getY()];
        this.characterPairMap = new HashMap<>();
        for (DefaultCharacter character : characters) {
            Pair<Integer, Integer> coords = GridUtils.getRandomCoords(grid);
            while (this.grid[coords.getLeft()][coords.getRight()] != null) {
                coords = GridUtils.getRandomCoords(grid);
            }
            this.grid[coords.getLeft()][coords.getRight()] = character;
            this.characterPairMap.put(character.getId(), coords);
        }
    }

    public Arena(List<DefaultCharacter> characters,
                 Grid gridPattern,
                 Map<String, Pair<Integer, Integer>> characterPairMap) {
        this.characters = characters;
        this.gridPattern = gridPattern;
        this.grid = new DefaultCharacter[gridPattern.getX()][gridPattern.getY()];
        this.characterPairMap = characterPairMap;
        for (DefaultCharacter character : characters) {
            Pair<Integer, Integer> coords = characterPairMap.get(character.getId());
            this.grid[coords.getLeft()][coords.getRight()] = character;
        }
    }

    public void move(DefaultCharacter character, Pair<Integer, Integer> newPosition) {
        if (newPosition.getLeft() >= grid.length || newPosition.getLeft() < 0
                || newPosition.getRight() >= grid[0].length || newPosition.getRight() < 0) {
            throw new IllegalArgumentException("you can't move out of the arena");
        }
        if (grid[newPosition.getLeft()][newPosition.getRight()] != null) {
            throw new IllegalArgumentException("this position is already used");
        }
        Pair<Integer, Integer> currentPosition = characterPairMap.get(character.getId());
        grid[currentPosition.getLeft()][currentPosition.getRight()] = null;
        grid[newPosition.getLeft()][newPosition.getRight()] = character;
        characterPairMap.put(character.getId(), newPosition);
    }

    public static ArenaBuilder builder() {
        return new ArenaBuilder();
    }

    public static class ArenaBuilder {
        private List<DefaultCharacter> characters;
        private Grid gridPattern;
        private Map<String, Pair<Integer, Integer>> characterPairMap;

        public ArenaBuilder gridPattern(Grid gridPattern) {
            this.gridPattern = gridPattern;
            return this;
        }

        public ArenaBuilder characters(List<DefaultCharacter> characters) {
            this.characters = characters;
            return this;
        }

        public ArenaBuilder characterPairMap(Map<String, Pair<Integer, Integer>> characterPairMap) {
            this.characterPairMap = characterPairMap;
            return this;
        }

        public Arena build() {
            if (this.characterPairMap != null) {
                return new Arena(characters, gridPattern, characterPairMap);
            } else {
                return new Arena(this.characters, this.gridPattern);
            }
        }
    }
}
