package org.thermoweb.rpg.logs;

import lombok.Builder;
import org.thermoweb.rpg.actions.Spells;
import org.thermoweb.rpg.utils.Damages;

import java.util.Optional;

@Builder
public record SpellLog(String roll, Status status, String outcome, String description, Spells spell, CharacterLog from,
                       CharacterLog target, Damages.DamagesLog damages) implements ActionLog {

    @Override
    public String toString() {
        return description;
    }

    public static SpellLogBuilder builder() {
        return new SpellLogBuilder() {
            @Override
            public SpellLog build() {
                preBuild();
                return super.build();
            }
        };
    }

    public static class SpellLogBuilder {
        void preBuild() {
            this.description(String.format("%s casts %s, %s making %s damages -> %s",
                    from.name(),
                    spell.name(),
                    roll,
                    Optional.ofNullable(damages)
                            .map(Damages.DamagesLog::toString)
                            .orElse("no"),
                    outcome));
        }
    }
}
