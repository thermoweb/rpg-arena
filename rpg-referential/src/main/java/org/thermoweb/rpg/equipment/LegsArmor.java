package org.thermoweb.rpg.equipment;

import lombok.Getter;
import org.thermoweb.rpg.equipment.slots.Legs;
import org.thermoweb.rpg.equipment.slots.Slots;

@Getter
public enum LegsArmor implements Equipment<Legs> {
    LEATHER_LEGS(0, 1);

    private final int defense;
    private final int armor;

    LegsArmor(int defense, int armor) {
        this.defense = defense;
        this.armor = armor;
    }

    public Slots getSlot() {
        return Slots.LEGS;
    }

    @Override
    public String getName() {
        return this.name();
    }
}
