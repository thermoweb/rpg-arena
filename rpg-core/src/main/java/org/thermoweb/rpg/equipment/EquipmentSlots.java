package org.thermoweb.rpg.equipment;

import org.thermoweb.rpg.equipment.slots.Back;
import org.thermoweb.rpg.equipment.slots.Feet;
import org.thermoweb.rpg.equipment.slots.Hands;
import org.thermoweb.rpg.equipment.slots.Head;
import org.thermoweb.rpg.equipment.slots.Legs;
import org.thermoweb.rpg.equipment.slots.Slot;
import org.thermoweb.rpg.equipment.slots.Torso;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class EquipmentSlots {

    private final Map<Class<? extends Slot>, WearableEquipment<? extends Slot>> slots;

    EquipmentSlots(Map<Class<? extends Slot>, WearableEquipment<? extends Slot>> slots) {
        this.slots = slots;
    }

    public Weapon getWeapon() {
        return (Weapon) slots.get(Hands.class).getEquipment();
    }

    public WearableEquipment<? extends Slot> getEquipment(Class<? extends Slot> slot) {
        return slots.get(slot);
    }

    public int getDefense() {
        return slots.values().stream()
                .filter(Objects::nonNull)
                .map(WearableEquipment::getEquipment)
                .map(Equipment::getDefense)
                .mapToInt(Integer::intValue)
                .sum();
    }

    public void putEquipment(WearableEquipment<? extends Slot> equipment, Class<? extends Slot> clazz) {
        slots.put(clazz, equipment);
    }

    public Map<Class<? extends Slot>, WearableEquipment<? extends Slot>> getSlots() {
        return this.slots;
    }

    public static EquipmentSlotsBuilder builder() {
        return new EquipmentSlotsBuilder();
    }

    public static class EquipmentSlotsBuilder {
        private Map<Class<? extends Slot>, WearableEquipment<? extends Slot>> slots;

        EquipmentSlotsBuilder() {
            slots = new HashMap<>();
            slots.put(Head.class, null);
            slots.put(Back.class, null);
            slots.put(Torso.class, null);
            slots.put(Hands.class, null);
            slots.put(Legs.class, null);
            slots.put(Feet.class, null);
        }

        public EquipmentSlotsBuilder withWeapon(Weapon weapon) {
            return withWeapon(weapon, 100);
        }

        public EquipmentSlotsBuilder withWeapon(Weapon weapon, int attrition) {
            this.slots.put(Hands.class, WearableEquipment.<Hands>builder().equipment(weapon).attrition(attrition).build());
            return this;
        }

        public EquipmentSlotsBuilder withHead(HeadArmor head) {
            return withHead(head, 100);
        }

        public EquipmentSlotsBuilder withHead(HeadArmor head, int attrition) {
            this.slots.put(Head.class, head.getWearableEquipment(attrition));
            return this;
        }

        public EquipmentSlotsBuilder withTorso(TorsoArmor torso) {
            return withTorso(torso, 100);
        }

        public EquipmentSlotsBuilder withTorso(TorsoArmor torso, int attrition) {
            this.slots.put(Torso.class, torso.getWearableEquipment(attrition));
            return this;
        }

        public EquipmentSlotsBuilder withLegs(LegsArmor legs) {
            return withLegs(legs, 100);
        }

        public EquipmentSlotsBuilder withLegs(LegsArmor legs, int attrition) {
            this.slots.put(Legs.class, legs.getWearableEquipment(attrition));
            return this;
        }

        public EquipmentSlotsBuilder equipment(Map<Class<? extends Slot>, WearableEquipment<? extends Slot>> slots) {
            this.slots = slots;
            return this;
        }

        public EquipmentSlots build() {
            return new EquipmentSlots(this.slots);
        }
    }
}
