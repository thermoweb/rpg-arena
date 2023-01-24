package org.thermoweb.rpg.encounter;

import org.thermoweb.rpg.logs.CombatLog;
import org.thermoweb.rpg.character.CharacterEntityMapper;
import org.thermoweb.rpg.characters.DefaultCharacter;
import org.thermoweb.rpg.data.encounters.EncounterEntity;
import org.thermoweb.rpg.encounter.states.CreatedState;
import org.thermoweb.rpg.encounter.states.EncounterState;
import org.thermoweb.rpg.encounter.states.FailedState;
import org.thermoweb.rpg.encounter.states.FinishedState;
import org.thermoweb.rpg.encounter.states.InProgressState;
import org.thermoweb.rpg.encounter.states.QueuedState;
import org.thermoweb.rpg.encounters.EncounterStatus;
import org.thermoweb.rpg.environment.Arena;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EncounterEntityMapper {

    public static Encounter map(EncounterEntity encounter) {
        List<DefaultCharacter> characters = encounter.getCharacters().stream().map(CharacterEntityMapper::map).toList();
        return Encounter.builder()
                .id(encounter.getId())
                .arena(Arena.builder().characters(characters).gridPattern(encounter.getGrid()).build())
                .state(map(encounter.getStatus()))
                .characters(characters)
                .combatLog(CombatLog.builder().logs(Optional.ofNullable(encounter.getCombatLog()).orElseGet(ArrayList::new)).build())
                .build();
    }

    public static EncounterEntity map(Encounter encounter) {
        return EncounterEntity.builder()
                .id(encounter.getId())
                .status(encounter.getState().getStatus())
                .grid(encounter.getArena().getGridPattern())
                .combatLog(encounter.getCombatLog().getLogs())
                .characters(encounter.getCharacters().stream().map(CharacterEntityMapper::map).toList())
                .build();
    }

    public static EncounterState map(EncounterStatus status) {
        return switch (status) {
            case CREATED -> new CreatedState();
            case QUEUED -> new QueuedState();
            case IN_PROGRESS -> new InProgressState();
            case FAILED -> new FailedState();
            case FINISHED -> new FinishedState();
        };
    }
}
