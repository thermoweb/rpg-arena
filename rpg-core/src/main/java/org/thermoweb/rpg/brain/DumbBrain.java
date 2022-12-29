package org.thermoweb.rpg.brain;

import org.thermoweb.rpg.actions.Action;
import org.thermoweb.rpg.actions.Attack;
import org.thermoweb.rpg.actions.Direction;
import org.thermoweb.rpg.actions.Move;
import org.thermoweb.rpg.characters.DefaultCharacter;
import org.thermoweb.rpg.environment.Arena;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class DumbBrain implements Brain {
    @Override
    public List<Action> getActions(DefaultCharacter defaultCharacter, Arena arena) {
        return arena.getCharacters().stream()
                .filter(c -> !Objects.equals(c.getId(), defaultCharacter.getId()))
                .findFirst()
                .map(target -> List.of(
                        Attack.builder()
                                .weapon(defaultCharacter.getEquipmentSlots().getWeapon())
                                .target(target).build(),
                        Move.builder()
                                .direction(Direction.getRandomDirection())
                                .build(),
                        Move.builder()
                                .direction(Direction.getRandomDirection())
                                .build(),
                        Move.builder()
                                .direction(Direction.getRandomDirection())
                                .build(),
                        Move.builder()
                                .direction(Direction.getRandomDirection())
                                .build(),
                        Move.builder()
                                .direction(Direction.getRandomDirection())
                                .build(),
                        Move.builder()
                                .direction(Direction.getRandomDirection())
                                .build(),
                        Move.builder()
                                .direction(Direction.getRandomDirection())
                                .build()
                        )
                )
                .orElseGet(Collections::emptyList);
    }
}
