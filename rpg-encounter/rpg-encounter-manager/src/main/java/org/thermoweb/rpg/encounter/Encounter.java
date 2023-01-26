package org.thermoweb.rpg.encounter;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.thermoweb.rpg.characters.DefaultCharacter;
import org.thermoweb.rpg.encounter.states.EncounterState;
import org.thermoweb.rpg.encounter.states.EncounterStateException;
import org.thermoweb.rpg.environment.Arena;
import org.thermoweb.rpg.logs.CombatLog;

import java.util.List;

@Builder
@Getter
@Setter
public class Encounter {

    private final String id;
    private final List<DefaultCharacter> characters;
    private final Arena arena;
    private CombatLog combatLog;
    private EncounterState state;

    public void prepare() throws EncounterStateException {
        state.prepare(this);
    }

    public void launch() throws EncounterStateException {
        state.launch(this);
    }

    public void run() throws EncounterStateException {
        state.run(this);
    }
}
