package org.thermoweb.rpg.encounter.states;

import lombok.extern.slf4j.Slf4j;
import org.thermoweb.rpg.actions.Action;
import org.thermoweb.rpg.actions.ActionException;
import org.thermoweb.rpg.actions.Attack;
import org.thermoweb.rpg.actions.Move;
import org.thermoweb.rpg.actions.TargetableAction;
import org.thermoweb.rpg.characters.DefaultCharacter;
import org.thermoweb.rpg.encounter.Encounter;
import org.thermoweb.rpg.encounters.EncounterStatus;
import org.thermoweb.rpg.environment.Arena;
import org.thermoweb.rpg.logs.ActionLog;
import org.thermoweb.rpg.logs.CombatLog;
import org.thermoweb.rpg.logs.RoundLog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
public final class InProgressState implements EncounterState {

    @Override
    public void run(Encounter encounter) {
        Arena arena = encounter.getArena();
        Map<Integer, DefaultCharacter> turnOrder = encounter.getCharacters().stream()
                .collect(Collectors.toMap(DefaultCharacter::rollInitiative, Function.identity()));
        int round = 0;

        List<RoundLog> roundLogs = new ArrayList<>();
        while (encounter.getCharacters().stream().filter(DefaultCharacter::isAlive).count() > 1) {
            RoundLog.RoundLogBuilder roundLog = RoundLog.builder().round(++round);
            Map<String, List<ActionLog>> logs = new HashMap<>();
            for (DefaultCharacter character : turnOrder.values()) {
                int maxActions = 1;
                int maxMove = character.getSpecies().getSpeed();
                List<Action> actionsDone = new ArrayList<>();
                List<Action> actions = character.getActions(arena);
                List<ActionLog> loggedActions = new ArrayList<>();
                for (Action action : actions) {
                    try {
                        if (action instanceof TargetableAction) {
                            setTargetAction(arena, (TargetableAction) action);
                        }
                        action.setOwner(character);
                        checkAction(action, actionsDone, maxActions, maxMove);
                        ActionLog actionLog = action.execute(arena);
                        loggedActions.add(actionLog);
                        log.info(actionLog.toString());
                        actionsDone.add(action);
                    } catch (ActionException e) {
                        log.warn("Action failed : {}", e.getMessage());
                    }
                }
                logs.put(character.getId(), loggedActions);
            }
            roundLog.logs(logs);
            roundLogs.add(roundLog.characters(encounter.getCharacters().stream().map(DefaultCharacter::getLog).toList()).build());
        }
        CombatLog.CombatLogBuilder combatLog = CombatLog.builder();
        Optional<DefaultCharacter> winner = arena.getCharacters().stream()
                .filter(DefaultCharacter::isAlive)
                .findAny();
        winner.ifPresent(c -> {
            log.info("{} wins the encounter", c.getName());
            combatLog.winner(c.getLog());
        });
        combatLog.logs(roundLogs);
        encounter.setCombatLog(combatLog.build());
        encounter.setState(new FinishedState());

        log.info("encounter {} ended", encounter.getId());
    }

    private static void setTargetAction(Arena arena, TargetableAction action) {
        action.setTarget(arena.getCharacters().stream()
                .filter(c -> c.getId().equals(action.getTarget().getId()))
                .findFirst()
                .orElseThrow());
    }

    private void checkAction(Action action, List<Action> actionsDone, int maxActions, int maxMove) throws ActionException {
        if (action instanceof TargetableAction) {
            if (actionsDone.stream().filter(a -> a instanceof TargetableAction).count() >= maxActions) {
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
