package org.thermoweb.rpg.equipment;

import org.thermoweb.rpg.equipment.slots.Slot;
import org.thermoweb.rpg.equipment.slots.Slots;

public sealed interface Equipment<T extends Slot> permits BackArmor, FeetsArmor, HeadArmor, LegsArmor, TorsoArmor, Weapon, WearableEquipment {

    default int getDefense() {
        return 0;
    }

    default int getArmor() {
        return 0;
    }

    String getName();

    Slots getSlot();

    default WearableEquipment<T> getWearableEquipment(int attrition) {
        return WearableEquipment.<T>builder()
                .equipment(this)
                .attrition(attrition)
                .build();
    }

    default WearableEquipment<T> getWearableEquipment() {
        return getWearableEquipment(100);
    }
}
