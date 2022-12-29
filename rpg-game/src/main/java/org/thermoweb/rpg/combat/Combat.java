package org.thermoweb.rpg.combat;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.thermoweb.rpg.actions.Action;
import org.thermoweb.rpg.actions.ActionException;
import org.thermoweb.rpg.characters.DefaultCharacter;
import org.thermoweb.rpg.environment.Arena;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Builder
@Slf4j
public class Combat {

    private final Arena arena;

    public void launch() {
        Map<Integer, DefaultCharacter> turnOrder = arena.getCharacters().stream()
                .collect(Collectors.toMap(DefaultCharacter::rollInitiative, Function.identity()));
        while (arena.getCharacters().stream().filter(DefaultCharacter::isAlive).count() > 1) {
            for (DefaultCharacter character : turnOrder.values()) {
                List<Action> actions = character.getActions(arena);
                for (Action action : actions) {
                    try {
                        action.setOwner(character);
                        action.execute(arena);
                    } catch (ActionException e) {
                        log.error("Action failed : {}", e.getMessage());
                    }
                }
            }
        }
        arena.getCharacters().stream()
                .filter(DefaultCharacter::isAlive)
                .findAny()
                .ifPresent(c -> log.info("{} wins the encounter", c.getName()));
    }
}
