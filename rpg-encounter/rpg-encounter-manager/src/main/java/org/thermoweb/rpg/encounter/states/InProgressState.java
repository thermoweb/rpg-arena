package org.thermoweb.rpg.encounter.states;

import lombok.extern.slf4j.Slf4j;
import org.thermoweb.rpg.actions.Action;
import org.thermoweb.rpg.actions.ActionException;
import org.thermoweb.rpg.actions.Attack;
import org.thermoweb.rpg.actions.Move;
import org.thermoweb.rpg.characters.DefaultCharacter;
import org.thermoweb.rpg.encounter.Encounter;
import org.thermoweb.rpg.encounters.EncounterStatus;
import org.thermoweb.rpg.environment.Arena;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
public final class InProgressState implements EncounterState {

    @Override
    public void run(Encounter encounter) {
        Arena arena = encounter.getArena();
        Map<Integer, DefaultCharacter> turnOrder = encounter.getCharacters().stream()
                .collect(Collectors.toMap(DefaultCharacter::rollInitiative, Function.identity()));
        while (encounter.getCharacters().stream().filter(DefaultCharacter::isAlive).count() > 1) {
            for (DefaultCharacter character : turnOrder.values()) {
                int maxActions = 1;
                int maxMove = character.getSpecies().getSpeed();
                List<Action> actionsDone = new ArrayList<>();
                List<Action> actions = character.getActions(arena);
                for (Action action : actions) {
                    try {
                        action.setOwner(character);
                        checkAction(action, actionsDone, maxActions, maxMove);
                        String actionLog = action.execute(arena);
                        actionsDone.add(action);
                        encounter.getCombatLog().getLogs().add(actionLog);
                    } catch (ActionException e) {
                        log.warn("Action failed : {}", e.getMessage());
                    }
                }
            }
        }
        encounter.setState(new FinishedState());
        log.info("encounter {} ended", encounter.getId());
        arena.getCharacters().stream()
                .filter(DefaultCharacter::isAlive)
                .findAny()
                .ifPresent(c -> log.info("{} wins the encounter", c.getName()));
    }

    private void checkAction(Action action, List<Action> actionsDone, int maxActions, int maxMove) throws ActionException {
        if (action instanceof Attack) {
            if (actionsDone.stream().filter(a -> a instanceof Attack).count() >= maxActions) {
                throw new ActionException("Character has reach his max attack actions");
            }
            return;
        }
        if (action instanceof Move) {
            if (actionsDone.stream().filter(a -> a instanceof Move).count() >= maxMove) {
                throw new ActionException("Character has reach his max move actions");
            }
        }
    }

    @Override
    public EncounterStatus getStatus() {
        return EncounterStatus.IN_PROGRESS;
    }
}
