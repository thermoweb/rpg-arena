package org.thermoweb.rpg.equipment.slots;

import org.thermoweb.rpg.equipment.Equipment;
import org.thermoweb.rpg.equipment.HeadArmor;
import org.thermoweb.rpg.equipment.LegsArmor;
import org.thermoweb.rpg.equipment.TorsoArmor;
import org.thermoweb.rpg.equipment.Weapon;

import java.util.Arrays;

public enum Slots implements Slot {
    HEAD(Head.class),
    TORSO(Torso.class),
    HANDS(Hands.class),
    LEGS(Legs.class);

    private final Class<? extends Slot> slot;

    Slots(Class<? extends Slot> slot) {
        this.slot = slot;
    }

    public static Equipment<? extends Slot> fromName(Slots slot, String name) {
        return switch (slot) {
            case HEAD -> HeadArmor.valueOf(name);
            case TORSO -> TorsoArmor.valueOf(name);
            case HANDS -> Weapon.valueOf(name);
            case LEGS -> LegsArmor.valueOf(name);
        };
    }

    public Class<? extends Slot> getSlot() {
        return this.slot;
    }

    public static Slots fromSlot(Class<? extends Slot> slot) {
        for (Slots value : values()) {
            if (value.getSlot().isAssignableFrom(slot)) {
                return value;
            }
        }
        return Arrays.stream(values()).filter(s -> s.getClass().isAssignableFrom(slot)).findFirst().orElseThrow();
    }
}
