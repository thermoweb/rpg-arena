package org.thermoweb.rpg.equipment;

import lombok.Getter;
import org.thermoweb.rpg.equipment.slots.Slots;
import org.thermoweb.rpg.equipment.slots.Torso;

@Getter
public enum TorsoArmor implements Equipment<Torso> {
    CHAIN_MAIL(1, 1);

    private final int defense;
    private final int armor;

    TorsoArmor(int defense, int armor) {
        this.defense = defense;
        this.armor = armor;
    }

    public Slots getSlot() {
        return Slots.TORSO;
    }

    @Override
    public String getName() {
        return this.name();
    }
}
