package org.thermoweb.rpg.encounter.states;

import org.thermoweb.rpg.encounter.Encounter;
import org.thermoweb.rpg.encounters.EncounterStatus;

public sealed interface EncounterState permits CreatedState, FailedState, FinishedState, InProgressState, QueuedState {
    default void prepare(Encounter encounter) throws EncounterStateException {
        throw new EncounterStateException("Can't prepare encounter.");
    }

    default void launch(Encounter encounter)throws EncounterStateException {
        throw new EncounterStateException("Can't launch encounter.");
    }

    default void run(Encounter encounter) throws EncounterStateException {
        throw new EncounterStateException("Can't run encounter");
    }

    EncounterStatus getStatus();
}
