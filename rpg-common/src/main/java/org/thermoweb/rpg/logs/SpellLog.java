package org.thermoweb.rpg.logs;

import lombok.Builder;
import org.thermoweb.rpg.actions.Spells;
import org.thermoweb.rpg.utils.Damages;

import java.util.Optional;

@Builder
public record SpellLog(String roll, Status status, String outcome, Spells spell, CharacterLog from,
                       CharacterLog target, Damages.DamagesLog damages) implements ActionLog {

    @Override
    public String toString() {
        return String.format("%s casts %s, %s making %s damages -> %s",
                from.name(),
                spell.name(),
                roll,
                Optional.ofNullable(damages)
                        .map(Damages.DamagesLog::toString)
                        .orElse("no"),
                outcome);
    }
}
