package org.thermoweb.rpg.encounter.states;

import org.thermoweb.rpg.encounter.Encounter;
import org.thermoweb.rpg.encounters.EncounterStatus;

public final class FinishedState implements EncounterState {

    @Override
    public void prepare(Encounter encounter) throws EncounterStateException {
        encounter.getCharacters().forEach(character -> character.setHitPoints(character.getMaxHitPoints()));
        encounter.getCombatLog().logs().clear();
        encounter.setState(new QueuedState());
    }

    @Override
    public EncounterStatus getStatus() {
        return EncounterStatus.FINISHED;
    }
}
