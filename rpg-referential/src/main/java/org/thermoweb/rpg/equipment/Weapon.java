package org.thermoweb.rpg.equipment;

import org.thermoweb.rpg.characters.Ability;
import org.thermoweb.rpg.equipment.slots.Hands;
import org.thermoweb.rpg.equipment.slots.Slots;
import org.thermoweb.rpg.utils.Damages;

public enum Weapon implements Equipment<Hands> {
    SWORD("1d6+1", 1, Ability.FORCE),
    DAGGER("1d6", 1, Ability.DEXTERITY),
    STAFF("1d4", 1, Ability.FORCE),
    BOW("1d6", 10, Ability.DEXTERITY),
    CROSSBOW("1d6+1", 10, Ability.DEXTERITY);

    private final Damages damages;
    private final int range;
    private final Ability ability;

    Weapon(String damages, int range, Ability ability) {
        this.damages = Damages.of(damages);
        this.range = range;
        this.ability = ability;
    }

    public int getDamages() {
        return damages.get();
    }

    public Damages.DamagesLog getLoggedDamages() {
        return damages.getLoggedDamages();
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
