package org.thermoweb.rpg.equipment;

import org.thermoweb.rpg.data.characters.CharacterEntity;
import org.thermoweb.rpg.equipment.slots.Slot;
import org.thermoweb.rpg.equipment.slots.Slots;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class EquipmentEntityMapper {

    public static Map<Slots, CharacterEntity.Equipment> map(EquipmentSlots equipmentSlots) {
        return equipmentSlots.getSlots()
                .entrySet()
                .stream()
                .collect(Collectors.toMap(
                        entry -> Slots.fromSlot(entry.getKey()),
                        entry -> CharacterEntity.Equipment.builder()
                                .name(entry.getValue().getName())
                                .attrition(entry.getValue().getAttrition())
                                .build()

                ));
    }

    public static EquipmentSlots map(Map<Slots, CharacterEntity.Equipment> equipmentMap) {
        Map<Class<? extends Slot>, WearableEquipment<? extends Slot>> slots = new HashMap<>();
        equipmentMap.forEach((slot, equipment) -> slots.put(
                        slot.getSlot(),
                        WearableEquipment.fromName(slot, equipment.getName(), equipment.getAttrition()))
                );
        return EquipmentSlots.builder().equipment(slots).build();
    }
}
