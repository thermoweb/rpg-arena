package org.thermoweb.rpg.rest.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.thermoweb.rpg.characters.Ability;
import org.thermoweb.rpg.characters.CharacterService;
import org.thermoweb.rpg.characters.Statistics;
import org.thermoweb.rpg.dto.CharacterDto;
import org.thermoweb.rpg.equipment.TorsoArmor;
import org.thermoweb.rpg.equipment.Weapon;
import org.thermoweb.rpg.rest.client.CharacterCreationRequest;
import org.thermoweb.rpg.rest.mapper.CharacterMapper;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/characters")
public class CharactersController {

    private final CharacterService characterService;

    public CharactersController(CharacterService characterService) {
        this.characterService = characterService;
    }

    @GetMapping
    public List<CharacterDto> findall() {
        return characterService.getAll().stream().map(CharacterMapper::map).toList();
    }

    @GetMapping("{id}")
    public CharacterDto findById(@PathVariable String id) {
        return characterService.getById(id).map(CharacterMapper::map).orElseThrow();
    }

    @PostMapping("/test-data")
    public List<CharacterDto> createTestData() {
        characterService.deleteAll();
        CharacterDto bbeg = CharacterDto.builder()
                .name("Kenny")
                .maxHitPoints(20)
                .hitPoints(20)
                .statistics(Map.of(Ability.FORCE, 50, Ability.DEXTERITY, 40, Ability.INTELLIGENCE, 30))
                .equipment(Map.of(Weapon.SWORD.getSlot(), CharacterDto.Equipment.builder().name(Weapon.SWORD.name()).attrition(100).build(),
                        TorsoArmor.CHAIN_MAIL.getSlot(), CharacterDto.Equipment.builder().name(TorsoArmor.CHAIN_MAIL.name()).attrition(100).build()))
                .build();
        CharacterDto chronie = CharacterDto.builder()
                .name("Chronie")
                .maxHitPoints(20)
                .hitPoints(20)
                .statistics(Map.of(Ability.FORCE, 40, Ability.DEXTERITY, 30, Ability.INTELLIGENCE, 50))
                .equipment(Map.of(Weapon.CROSSBOW.getSlot(), CharacterDto.Equipment.builder().name(Weapon.CROSSBOW.name()).attrition(100).build(),
                        TorsoArmor.CHAIN_MAIL.getSlot(), CharacterDto.Equipment.builder().name(TorsoArmor.CHAIN_MAIL.name()).attrition(100).build()))
                .build();
        characterService.create(CharacterMapper.map(chronie));
        characterService.create(CharacterMapper.map(bbeg));
        return findall();
    }

    @PostMapping
    public CharacterDto create(@RequestBody CharacterCreationRequest request) {
        Statistics startStats = request.statistics().getStatistics()
                .merge(request.profile().getStatistics())
                .merge(request.species().getStatistics());

        CharacterDto characterDto = CharacterDto.builder()
                .name(request.name())
                .species(request.species())
                .profiles(request.profile())
                .hitPoints(20)
                .maxHitPoints(20)
                .statistics(Map.of(Ability.FORCE, startStats.force(), Ability.DEXTERITY, startStats.dexterity(), Ability.INTELLIGENCE, startStats.intelligence()))
                .equipment(request.equipment())
                .build();
        return CharacterMapper.map(characterService.create(CharacterMapper.map(characterDto)));
    }
}
