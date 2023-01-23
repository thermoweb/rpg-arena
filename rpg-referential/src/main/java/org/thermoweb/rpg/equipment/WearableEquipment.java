package org.thermoweb.rpg.equipment;

import lombok.Builder;
import lombok.Getter;
import org.thermoweb.rpg.equipment.slots.Slot;
import org.thermoweb.rpg.equipment.slots.Slots;

@Builder
@Getter
public final class WearableEquipment<T extends Slot> implements Equipment<T> {

    private final Equipment<T> equipment;
    private int attrition;

    public static WearableEquipment<?> fromName(Slots slot, String name, int attrition) {
        return Slots.fromName(slot, name).getWearableEquipment(attrition);
    }

    @Override
    public String getName() {
        return equipment.getName();
    }

    @Override
    public Slots getSlot() {
        return equipment.getSlot();
    }
}
