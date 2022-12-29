package org.thermoweb.rpg;

import lombok.extern.slf4j.Slf4j;
import org.thermoweb.rpg.arena.Grid;
import org.thermoweb.rpg.brain.DumbBrain;
import org.thermoweb.rpg.characters.DefaultCharacter;
import org.thermoweb.rpg.characters.Statistics;
import org.thermoweb.rpg.combat.Combat;
import org.thermoweb.rpg.environment.Arena;
import org.thermoweb.rpg.equipment.EquipmentSlots;
import org.thermoweb.rpg.equipment.HeadArmor;
import org.thermoweb.rpg.equipment.LegsArmor;
import org.thermoweb.rpg.equipment.TorsoArmor;
import org.thermoweb.rpg.equipment.Weapon;
import org.thermoweb.rpg.utils.GridUtils;

import java.util.List;

@Slf4j
public class Main {
    public static void main(String[] args) {
        log.info("RPG Arena");

        DefaultCharacter player = DefaultCharacter.builder()
                .name("Adventurer")
                .maxHitPoints(20)
                .hitPoints(20)
                .statistics(new Statistics(50, 40, 30))
                .brain(new DumbBrain())
                .equipmentSlots(EquipmentSlots.builder()
                        .withWeapon(Weapon.SWORD)
                        .withHead(HeadArmor.LEATHER_CAP)
                        .withLegs(LegsArmor.LEATHER_LEGS)
                        .withTorso(TorsoArmor.CHAIN_MAIL).build())
                .build();
        DefaultCharacter opponent = DefaultCharacter.builder()
                .name("BBEG")
                .maxHitPoints(20)
                .hitPoints(20)
                .brain(new DumbBrain())
                .statistics(new Statistics(50, 40, 30))
                .equipmentSlots(EquipmentSlots.builder()
                        .withTorso(TorsoArmor.CHAIN_MAIL)
                        .withWeapon(Weapon.SWORD).build())
                .build();
        ;

        Arena arena = Arena.builder().characters(List.of(player, opponent)).gridPattern(Grid.SQUARE_8).build();

        Combat combat = Combat.builder().arena(arena).build();

        GridUtils.consolePrint(arena);

        combat.launch();

        GridUtils.consolePrint(arena);
        int distance = GridUtils.getDistance(arena.getCharacterPairMap().get(player.getId()), arena.getCharacterPairMap().get(opponent.getId()));
        double directDistance = GridUtils.getDirectDistance(arena.getCharacterPairMap().get(player.getId()), arena.getCharacterPairMap().get(opponent.getId()));
        log.info("distance between opponents : {} ({} direct)", distance, directDistance);
    }
}