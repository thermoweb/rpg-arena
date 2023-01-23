package org.thermoweb.rpg.actions;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.thermoweb.core.utils.RandomUtils;
import org.thermoweb.rpg.characters.DefaultCharacter;
import org.thermoweb.rpg.environment.Arena;
import org.thermoweb.rpg.equipment.Weapon;
import org.thermoweb.rpg.equipment.WearableEquipment;
import org.thermoweb.rpg.utils.Dice;
import org.thermoweb.rpg.utils.GridUtils;

import java.util.Objects;
import java.util.Optional;

import static java.lang.Math.max;

@Builder
@Getter
@Slf4j
public final class Attack implements Action {

    private final Weapon weapon;
    private final DefaultCharacter target;
    private DefaultCharacter from;

    @Override
    public String execute(Arena arena) throws ActionException {
        autoCheck();
        if (GridUtils.getDirectDistance(arena.getCharacterPairMap().get(from.getId()), arena.getCharacterPairMap().get(target.getId())) > weapon.getRange()) {
            throw new ActionException("target is too far to attack.");
        }

        int roll = Dice.D100.roll();
        int abilityThreshold = from.getStatistics().getAbility(weapon.getAbility());

        if (roll < abilityThreshold) {
            String actionLog = String.format("roll %d against %d -> attacks missed...", roll, abilityThreshold);
            log.info(actionLog);
            return actionLog;
        } else {
            int damages = weapon.getDamages();
            int armorDamage = Optional.ofNullable(RandomUtils.getRandomItem(target.getEquipmentSlots().getSlots()))
                    .map(WearableEquipment::getArmor)
                    .orElse(0);
            int damagesTaken = max(damages - target.getEquipmentSlots().getDefense() - armorDamage, 0);
            target.takeDamage(damagesTaken);

            String actionLog = String.format("%s taking %d damages (%d raw). %d hit points left", target.getName(), damagesTaken, damages, target.getHitPoints());
            log.info(actionLog);


            return actionLog;
        }
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
}
