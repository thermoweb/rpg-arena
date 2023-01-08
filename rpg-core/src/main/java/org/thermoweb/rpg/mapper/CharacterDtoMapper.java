package org.thermoweb.rpg.mapper;

import org.thermoweb.rpg.brain.Brain;
import org.thermoweb.rpg.brain.DumbBrain;
import org.thermoweb.rpg.brain.RemoteBrain;
import org.thermoweb.rpg.characters.Ability;
import org.thermoweb.rpg.characters.BrainType;
import org.thermoweb.rpg.characters.DefaultCharacter;
import org.thermoweb.rpg.characters.Statistics;
import org.thermoweb.rpg.dto.CharacterDto;
import org.thermoweb.rpg.dto.brain.BrainDto;
import org.thermoweb.rpg.equipment.EquipmentSlots;
import org.thermoweb.rpg.equipment.WearableEquipment;
import org.thermoweb.rpg.equipment.slots.Slot;
import org.thermoweb.rpg.equipment.slots.Slots;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class CharacterDtoMapper {

    public static DefaultCharacter map(CharacterDto characterDto) {
        Map<Class<? extends Slot>, WearableEquipment<? extends Slot>> slots = new HashMap<>();
        characterDto.equipment()
                .forEach((slot, equipment) -> slots.put(
                        slot.getSlot(),
                        WearableEquipment.fromName(slot, equipment.name(), equipment.attrition()))
                );

        return DefaultCharacter.builder()
                .id(characterDto.id())
                .name(characterDto.name())
                .species(characterDto.species())
                .profiles(characterDto.profiles())
                .brain(switch (Optional.ofNullable(characterDto.brain()).map(BrainDto::type).orElse(BrainType.LOCAL)) {
                    case LOCAL -> new DumbBrain();
                    case REMOTE -> new RemoteBrain(characterDto.brain().uri());
                })
                .hitPoints(characterDto.hitPoints())
                .maxHitPoints(characterDto.maxHitPoints())
                .equipmentSlots(EquipmentSlots.builder().equipment(slots).build())
                .spellbook(characterDto.spellbook())
                .statistics(Statistics.builder()
                        .force(characterDto.statistics().get(Ability.FORCE))
                        .dexterity(characterDto.statistics().get(Ability.DEXTERITY))
                        .intelligence(characterDto.statistics().get(Ability.INTELLIGENCE))
                        .build())
                .build();
    }

    public static CharacterDto map(DefaultCharacter character) {
        Map<Slots, CharacterDto.Equipment> equipment = new HashMap<>();
        character.getEquipmentSlots().getSlots().forEach((aClass, wearableEquipment) -> equipment
                .put(Slots.fromSlot(aClass), new CharacterDto.Equipment(wearableEquipment.getName(), wearableEquipment.getAttrition())));
        Map<Ability, Integer> statistics = new HashMap<>();
        statistics.put(Ability.FORCE, character.getStatistics().force());
        statistics.put(Ability.DEXTERITY, character.getStatistics().dexterity());
        statistics.put(Ability.INTELLIGENCE, character.getStatistics().intelligence());
        return CharacterDto.builder()
                .id(character.getId())
                .name(character.getName())
                .species(character.getSpecies())
                .profiles(character.getProfiles())
                .statistics(statistics)
                .spellbook(character.getSpellbook())
                .brain(mapBrain(character.getBrain()))
                .hitPoints(character.getHitPoints())
                .maxHitPoints(character.getMaxHitPoints())
                .equipment(equipment)
                .build();
    }

    private static BrainDto mapBrain(Brain brain) {
        if (brain == null) {
            return null;
        }
        return switch (brain.getType()) {
            case LOCAL -> new BrainDto(BrainType.LOCAL, null);
            case REMOTE -> new BrainDto(BrainType.REMOTE, ((RemoteBrain) brain).getUri());
        };
    }
}
