package org.thermoweb.rpg.rest.mapper;

import org.thermoweb.rpg.characters.Ability;
import org.thermoweb.rpg.data.characters.CharacterEntity;
import org.thermoweb.rpg.dto.CharacterDto;

import java.util.Map;
import java.util.stream.Collectors;

public class CharacterMapper {

    public static CharacterDto map(CharacterEntity characterEntity) {
        return CharacterDto.builder()
                .id(characterEntity.getId())
                .name(characterEntity.getName())
                .profiles(characterEntity.getProfiles())
                .species(characterEntity.getSpecies())
                .maxHitPoints(characterEntity.getMaxHitPoints())
                .hitPoints(characterEntity.getHitPoints())
                .equipment(characterEntity.getEquipment()
                        .entrySet()
                        .stream()
                        .collect(Collectors.toMap(
                                Map.Entry::getKey,
                                entry -> CharacterDto.Equipment.builder()
                                        .name(entry.getValue().getName())
                                        .attrition(entry.getValue().getAttrition())
                                        .build())))
                .spellbook(characterEntity.getSpellbook())
                .statistics(Map.of(Ability.FORCE, characterEntity.getStatistics().getForce(),
                        Ability.DEXTERITY, characterEntity.getStatistics().getDexterity(),
                        Ability.INTELLIGENCE, characterEntity.getStatistics().getIntelligence()))
                .build();

    }

    public static CharacterEntity map(CharacterDto characterDto) {
        return CharacterEntity.builder()
                .id(characterDto.id())
                .name(characterDto.name())
                .profiles(characterDto.profiles())
                .species(characterDto.species())
                .hitPoints(characterDto.hitPoints())
                .maxHitPoints(characterDto.maxHitPoints())
                .spellbook(characterDto.spellbook())
                .equipment(characterDto.equipment()
                        .entrySet()
                        .stream()
                        .collect(Collectors.toMap(
                                Map.Entry::getKey,
                                entry -> CharacterEntity.Equipment.builder()
                                        .name(entry.getValue().name())
                                        .attrition(entry.getValue().attrition())
                                        .build())))
                .statistics(CharacterEntity.Statistics.builder()
                        .force(characterDto.statistics().get(Ability.FORCE))
                        .dexterity(characterDto.statistics().get(Ability.DEXTERITY))
                        .intelligence(characterDto.statistics().get(Ability.INTELLIGENCE))
                        .build())
                .build();
    }
}
