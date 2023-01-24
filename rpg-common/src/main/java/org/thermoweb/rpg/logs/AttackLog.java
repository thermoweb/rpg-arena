package org.thermoweb.rpg.logs;

import lombok.Builder;
import org.thermoweb.core.data.Pair;
import org.thermoweb.rpg.equipment.Weapon;
import org.thermoweb.rpg.utils.Damages;

@Builder
public record AttackLog(String roll, String outcome, Pair<String, String> from,
                        Pair<String, String> target, Weapon weapon, Damages.DamagesLog damages) implements ActionLog {
}
