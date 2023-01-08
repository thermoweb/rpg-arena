package org.thermoweb.rpg.characters;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.thermoweb.core.utils.RandomUtils;
import org.thermoweb.rpg.actions.Action;
import org.thermoweb.rpg.actions.Spells;
import org.thermoweb.rpg.brain.Brain;
import org.thermoweb.rpg.environment.Arena;
import org.thermoweb.rpg.equipment.EquipmentSlots;
import org.thermoweb.rpg.equipment.WearableEquipment;
import org.thermoweb.rpg.utils.Dice;

import java.util.List;
import java.util.Optional;

import static java.lang.Math.max;

@Getter
@Setter
@Builder
@Slf4j
public class DefaultCharacter {

    private final String id;
    private final String name;
    private final Species species;
    private final Profiles profiles;
    private final int maxHitPoints;
    private int hitPoints;
    private final Statistics statistics;
    private final EquipmentSlots equipmentSlots;
    private final List<Spells> spellbook;
    private final Brain brain;

    public int rollInitiative() {
        return Dice.D100.roll() + statistics.getAbility(Ability.DEXTERITY);
    }

    public boolean isAlive() {
        return hitPoints > 0;
    }

    public boolean rollAbility(Ability ability) {
        int roll = Dice.D100.roll();
        int threshold = statistics.getAbility(ability);
        log.info("roll : {} against {} ({})", roll, threshold, ability.name());
        return roll <= threshold;
    }

    public String takeDamage(int damages) {
        int armorDamage = Optional.ofNullable(RandomUtils.getRandomItem(equipmentSlots.getSlots()))
                .map(WearableEquipment::getArmor)
                .orElse(0);
        int damagesTaken = max(damages - equipmentSlots.getDefense() - armorDamage, 0);
        hitPoints -= damagesTaken;
        String actionLog = String.format("%s taking %d damages (%d raw). %d hit points left", name, damagesTaken, damages, hitPoints);
        log.info(actionLog);

        return actionLog;
    }

    public String takeSpellDamages(int damages) {
        hitPoints -= damages;
        String actionLog = String.format("%s taking %d damages (%d raw). %d hit points left", name, damages, damages, hitPoints);
        log.info(actionLog);

        return actionLog;
    }

    public void spellCostHp(int hpCost) {
        hitPoints -= hpCost;
    }

    public List<Action> getActions(Arena arena) {
        return brain.getActions(this, arena);
    }
}
