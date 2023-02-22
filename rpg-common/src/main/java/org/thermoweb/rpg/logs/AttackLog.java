package org.thermoweb.rpg.logs;

import lombok.Builder;
import org.thermoweb.rpg.equipment.Weapon;
import org.thermoweb.rpg.utils.Damages;

import java.util.Optional;

@Builder
public record AttackLog(String roll, Status status, String outcome, String description, CharacterLog from,
                        CharacterLog target, Weapon weapon, Damages.DamagesLog damages) implements ActionLog {

    @Override
    public String toString() {
        return description;
    }

    public static AttackLogBuilder builder() {
        return new AttackLogBuilder() {
            @Override
            public AttackLog build() {
                preBuild();
                return super.build();
            }
        };
    }

    public static class AttackLogBuilder {

        void preBuild() {
            this.description(String.format("%s attacks with %s, %s making %s damages -> %s",
                    from.name(),
                    weapon.name(),
                    roll,
                    Optional.ofNullable(damages)
                            .map(Damages.DamagesLog::toString)
                            .orElse("no"),
                    outcome));
        }
    }
}
