package org.thermoweb.rpg.character;

import org.thermoweb.rpg.brain.Brain;
import org.thermoweb.rpg.brain.DumbBrain;
import org.thermoweb.rpg.brain.RemoteBrain;
import org.thermoweb.rpg.characters.BrainType;
import org.thermoweb.rpg.characters.DefaultCharacter;
import org.thermoweb.rpg.characters.Statistics;
import org.thermoweb.rpg.data.characters.CharacterEntity;
import org.thermoweb.rpg.equipment.EquipmentEntityMapper;

public class CharacterEntityMapper {

    public static CharacterEntity map(DefaultCharacter character) {
        return CharacterEntity.builder()
                .id(character.getId())
                .name(character.getName())
                .profiles(character.getProfiles())
                .species(character.getSpecies())
                .brain(mapBrain(character.getBrain()))
                .hitPoints(character.getHitPoints())
                .maxHitPoints(character.getMaxHitPoints())
                .statistics(map(character.getStatistics()))
                .equipment(EquipmentEntityMapper.map(character.getEquipmentSlots()))
                .build();
    }

    private static CharacterEntity.BrainEntity mapBrain(Brain brain) {
        if (brain == null) {
            return null;
        }
        return switch (brain.getType()) {

            case LOCAL -> CharacterEntity.BrainEntity.builder().brainType(BrainType.LOCAL).build();
            case REMOTE -> CharacterEntity.BrainEntity.builder()
                    .brainType(BrainType.REMOTE)
                    .uri(((RemoteBrain) brain).getUri())
                    .build();
        };
    }

    public static DefaultCharacter map(CharacterEntity characterEntity) {
        return DefaultCharacter.builder()
                .id(characterEntity.getId())
                .name(characterEntity.getName())
                .profiles(characterEntity.getProfiles())
                .species(characterEntity.getSpecies())
                .brain(mapBrain(characterEntity.getBrain()))
                .maxHitPoints(characterEntity.getMaxHitPoints())
                .hitPoints(characterEntity.getHitPoints())
                .statistics(map(characterEntity.getStatistics()))
                .equipmentSlots(EquipmentEntityMapper.map(characterEntity.getEquipment()))
                .build();
    }

    private static Brain mapBrain(CharacterEntity.BrainEntity brain) {
        if (brain == null) {
            return null;
        }
        return switch (brain.getBrainType()) {

            case LOCAL -> new DumbBrain();
            case REMOTE -> new RemoteBrain(brain.getUri());
        };
    }

    private static Statistics map(CharacterEntity.Statistics statistics) {
        return Statistics.builder()
                .force(statistics.getForce())
                .dexterity(statistics.getDexterity())
                .intelligence(statistics.getIntelligence())
                .build();
    }

    private static CharacterEntity.Statistics map(Statistics statistics) {
        return CharacterEntity.Statistics.builder()
                .force(statistics.force())
                .dexterity(statistics.dexterity())
                .intelligence(statistics.intelligence())
                .build();
    }
}
