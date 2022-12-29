package org.thermoweb.rpg.equipment;

import org.thermoweb.rpg.characters.Ability;
import org.thermoweb.rpg.equipment.slots.Hands;
import org.thermoweb.rpg.equipment.slots.Slots;
import org.thermoweb.rpg.utils.Damages;
import org.thermoweb.rpg.utils.Dice;

public enum Weapon implements Equipment<Hands> {
    SWORD(Dice.D6, 1, 1, Ability.FORCE),
    STAFF(Dice.D4, 1, 0, Ability.FORCE),
    CROSSBOW(Dice.D6, 10, 1, Ability.DEXTERITY);

    private final Damages damages;
    private final int range;
    private final Ability ability;

    Weapon(Dice dice, int range, int bonus, Ability ability) {
        this.damages = Damages.builder().dice(dice).bonus(bonus).build();
        this.range = range;
        this.ability = ability;
    }

    public int getDamages() {
        return damages.get();
    }

    public int getRange() {
        return range;
    }

    public Slots getSlot() {
        return Slots.HANDS;
    }

    public Ability getAbility() {
        return ability;
    }

    @Override
    public String getName() {
        return this.name();
    }
}
