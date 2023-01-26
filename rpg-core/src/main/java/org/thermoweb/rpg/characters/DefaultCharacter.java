package org.thermoweb.rpg.characters;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.thermoweb.rpg.actions.Action;
import org.thermoweb.rpg.actions.Spells;
import org.thermoweb.rpg.brain.Brain;
import org.thermoweb.rpg.environment.Arena;
import org.thermoweb.rpg.equipment.EquipmentSlots;
import org.thermoweb.rpg.logs.CharacterLog;
import org.thermoweb.rpg.utils.Dice;

import java.util.List;
import java.util.Set;

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
    private final Set<Skills> skills;
    private final EquipmentSlots equipmentSlots;
    private final List<Spells> spellbook;
    private final Brain brain;

    public int rollInitiative() {
        return Dice.D100.roll() + statistics.getAbility(Ability.DEXTERITY);
    }

    public boolean isAlive() {
        return hitPoints > 0;
    }

    public void takeDamage(int damages) {
        hitPoints -= damages;
    }

    public void spellCostHp(int hpCost) {
        hitPoints -= hpCost;
    }

    public List<Action> getActions(Arena arena) {
        return brain.getActions(this, arena);
    }

    public CharacterLog getLog() {
        return CharacterLog.builder().id(this.id).name(this.name).hitPoints(this.hitPoints).build();
    }
}
