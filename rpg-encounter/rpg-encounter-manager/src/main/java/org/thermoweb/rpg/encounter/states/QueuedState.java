package org.thermoweb.rpg.encounter.states;

import lombok.extern.slf4j.Slf4j;
import org.thermoweb.rpg.encounter.Encounter;
import org.thermoweb.rpg.encounters.EncounterStatus;

@Slf4j
public final class QueuedState implements EncounterState {

    @Override
    public void run(Encounter encounter) throws EncounterStateException {
        encounter.setState(new InProgressState());
    }

    @Override
    public void launch(Encounter encounter) throws EncounterStateException {
        encounter.setState(new InProgressState());
    }

    @Override
    public EncounterStatus getStatus() {
        return EncounterStatus.QUEUED;
    }
}
