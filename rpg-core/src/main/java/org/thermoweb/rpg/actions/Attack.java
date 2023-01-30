package org.thermoweb.rpg.actions;

import lombok.Builder;
import lombok.Getter;
import org.thermoweb.core.utils.RandomUtils;
import org.thermoweb.rpg.characters.DefaultCharacter;
import org.thermoweb.rpg.environment.Arena;
import org.thermoweb.rpg.equipment.Weapon;
import org.thermoweb.rpg.equipment.WearableEquipment;
import org.thermoweb.rpg.logs.ActionLog;
import org.thermoweb.rpg.logs.AttackLog;
import org.thermoweb.rpg.utils.Damages;
import org.thermoweb.rpg.utils.Dice;
import org.thermoweb.rpg.utils.GridUtils;

import java.util.Objects;
import java.util.Optional;

import static java.lang.Math.max;

@Builder
@Getter
public final class Attack implements TargetableAction {

    private final Weapon weapon;
    private DefaultCharacter target;
    private DefaultCharacter from;

    @Override
    public ActionLog execute(Arena arena) throws ActionException {
        autoCheck();
        if (getDirectDistance(arena) > weapon.getRange()) {
            throw new ActionException("target is too far to attack.");
        }

        int roll = Dice.D100.roll();
        int abilityThreshold = from.getStatistics().getAbility(weapon.getAbility());
        AttackLog.AttackLogBuilder attackLog = AttackLog.builder()
                .from(from.getLog())
                .target(target.getLog())
                .weapon(weapon)
                .roll(String.format("roll %d on %s (%d)", roll, weapon.getAbility(), abilityThreshold));

        if (roll > abilityThreshold) {
            return attackLog.status(ActionLog.Status.FAILED).outcome("attacks missed...").build();
        } else {
            Damages.DamagesLog loggedDamages = weapon.getLoggedDamages();
            int armorDamage = Optional.ofNullable(RandomUtils.getRandomItem(target.getEquipmentSlots().getSlots()))
                    .map(WearableEquipment::getArmor)
                    .orElse(0);
            int damagesTaken = max(loggedDamages.total() - target.getEquipmentSlots().getDefense() - armorDamage, 0);
            target.takeDamage(damagesTaken);

            return attackLog
                    .status(ActionLog.Status.SUCCESS)
                    .damages(loggedDamages)
                    .outcome(String.format("%s taking %d damages (%d raw). %d hit points left",
                            target.getName(),
                            damagesTaken,
                            loggedDamages.total(),
                            target.getHitPoints()))
                    .build();
        }
    }

    private double getDirectDistance(Arena arena) {
        return Math.round(GridUtils
                .getDirectDistance(arena.getCharacterPairMap().get(from.getId()),
                        arena.getCharacterPairMap().get(target.getId())));
    }

    @Override
    public void setOwner(DefaultCharacter owner) {
        this.from = owner;
    }

    private void autoCheck() throws ActionException {
        try {
            Objects.requireNonNull(weapon, "'weapon' should be not null");
            Objects.requireNonNull(from, "'from' should be not null");
            Objects.requireNonNull(target, "'target' should be not null");
        } catch (NullPointerException e) {
            throw new ActionException("Action is not valid", e);
        }
    }

    @Override
    public void setTarget(DefaultCharacter character) {
        this.target = character;
    }

    @Override
    public Damages getDamages() {
        return weapon.getDamages();
    }
}
