package org.thermoweb.rpg.encounter.states;

import lombok.extern.slf4j.Slf4j;
import org.thermoweb.rpg.encounter.Encounter;
import org.thermoweb.rpg.encounters.EncounterStatus;

@Slf4j
public final class CreatedState implements EncounterState {

    @Override
    public void prepare(Encounter encounter) {
        log.info("Launching encounter {}", encounter.getId());
        encounter.setState(new QueuedState());
    }

    @Override
    public EncounterStatus getStatus() {
        return EncounterStatus.CREATED;
    }
}