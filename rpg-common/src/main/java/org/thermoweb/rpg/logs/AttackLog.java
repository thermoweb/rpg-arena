package org.thermoweb.rpg.logs;

import lombok.Builder;
import org.thermoweb.rpg.equipment.Weapon;
import org.thermoweb.rpg.utils.Damages;

import java.util.Optional;

@Builder
public record AttackLog(String roll, Status status, String outcome, CharacterLog from, CharacterLog target,
                        Weapon weapon, Damages.DamagesLog damages) implements ActionLog {

    @Override
    public String toString() {
        return String.format("%s attacks with %s, %s making %s damages -> %s",
                from.name(),
                weapon.name(),
                roll,
                Optional.ofNullable(damages)
                        .map(Damages.DamagesLog::toString)
                        .orElse("no"),
                outcome);
    }
}
