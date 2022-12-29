package org.thermoweb.rpg.equipment;

import lombok.Getter;
import org.thermoweb.rpg.equipment.slots.Head;
import org.thermoweb.rpg.equipment.slots.Slots;

@Getter
public enum HeadArmor implements Equipment<Head> {
    LEATHER_CAP(0, 1);

    private final int defense;
    private final int armor;

    HeadArmor(int defense, int armor) {
        this.defense = defense;
        this.armor = armor;
    }

    public Slots getSlot() {
        return Slots.HEAD;
    }

    @Override
    public String getName() {
        return this.name();
    }
}
