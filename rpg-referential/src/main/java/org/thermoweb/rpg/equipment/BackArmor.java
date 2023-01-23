package org.thermoweb.rpg.equipment;

import lombok.Getter;
import org.thermoweb.rpg.equipment.slots.Back;
import org.thermoweb.rpg.equipment.slots.Feets;
import org.thermoweb.rpg.equipment.slots.Slots;

@Getter
public enum BackArmor implements Equipment<Back> {
    LEATHER_CLOACK(0, 1);

    private final int defense;
    private final int armor;

    BackArmor(int defense, int armor) {
        this.defense = defense;
        this.armor = armor;
    }

    public Slots getSlot() {
        return Slots.BACK;
    }

    @Override
    public String getName() {
        return this.name();
    }
}
