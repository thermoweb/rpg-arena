package org.thermoweb.rpg.equipment;

import lombok.Getter;
import org.thermoweb.rpg.equipment.slots.Feets;
import org.thermoweb.rpg.equipment.slots.Legs;
import org.thermoweb.rpg.equipment.slots.Slots;

@Getter
public enum FeetsArmor implements Equipment<Feets> {
    LEATHER_BOOTS(0, 1);

    private final int defense;
    private final int armor;

    FeetsArmor(int defense, int armor) {
        this.defense = defense;
        this.armor = armor;
    }

    public Slots getSlot() {
        return Slots.FEETS;
    }

    @Override
    public String getName() {
        return this.name();
    }
}
