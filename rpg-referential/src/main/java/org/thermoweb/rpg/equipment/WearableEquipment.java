package org.thermoweb.rpg.equipment;

import lombok.Builder;
import lombok.Getter;
import org.thermoweb.rpg.equipment.slots.Hands;
import org.thermoweb.rpg.equipment.slots.Slot;
import org.thermoweb.rpg.equipment.slots.Slots;

@Builder
@Getter
public final class WearableEquipment<T extends Slot> implements Equipment<T> {

    private final Equipment<T> equipment;
    private int attrition;

    public static WearableEquipment<?> fromName(Slots slot, String name, int attrition) {
        return switch (slot) {
            case HEAD -> HeadArmor.valueOf(name).getWearableEquipment(attrition);
            case TORSO -> TorsoArmor.valueOf(name).getWearableEquipment();
            case HANDS -> Weapon.valueOf(name).getWearableEquipment();
            case LEGS -> LegsArmor.valueOf(name).getWearableEquipment(attrition);
        };
    }

    @Override
    public String getName() {
        return equipment.getName();
    }
}
