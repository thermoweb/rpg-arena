package org.thermoweb.rpg.logs;

import lombok.Builder;
import org.thermoweb.core.data.Pair;
import org.thermoweb.rpg.actions.Spells;
import org.thermoweb.rpg.utils.Damages;

@Builder
public record SpellLog(String roll, String outcome, Spells spell, CharacterLog from,
                       CharacterLog target, Damages.DamagesLog damages) implements ActionLog {
}
