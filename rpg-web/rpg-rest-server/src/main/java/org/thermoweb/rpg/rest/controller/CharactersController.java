package org.thermoweb.rpg.rest.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.thermoweb.core.utils.RandomUtils;
import org.thermoweb.rpg.actions.Spells;
import org.thermoweb.rpg.characters.Ability;
import org.thermoweb.rpg.characters.CharacterService;
import org.thermoweb.rpg.characters.Skills;
import org.thermoweb.rpg.characters.Statistics;
import org.thermoweb.rpg.dto.CharacterDto;
import org.thermoweb.rpg.equipment.Equipment;
import org.thermoweb.rpg.rest.client.CharacterCreationRequest;
import org.thermoweb.rpg.rest.mapper.CharacterMapper;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

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

    @PostMapping
    public CharacterDto create(@RequestBody CharacterCreationRequest request) {
        Statistics startStats = request.statistics().getStatistics()
                .merge(request.profile().getStatistics())
                .merge(request.species().getStatistics());

        Set<Skills> skills = request.profile().getSkills();
        CharacterDto characterDto = CharacterDto.builder()
                .name(request.name())
                .species(request.species())
                .profiles(request.profile())
                .hitPoints(20)
                .maxHitPoints(20)
                .statistics(Map.of(Ability.FORCE, startStats.force(), Ability.DEXTERITY, startStats.dexterity(), Ability.INTELLIGENCE, startStats.intelligence()))
                .skills(skills)
                .spellbook(skills.contains(Skills.SPELL_CASTING) ? getRandomSpells() : Collections.emptyList())
                .equipment(request.profile().getStarterSet().stream().collect(Collectors.toMap(Equipment::getSlot,
                        e -> CharacterDto.Equipment.builder().name(e.getName()).attrition(100).build())))
                .build();
        return CharacterMapper.map(characterService.create(CharacterMapper.map(characterDto)));
    }

    private static List<Spells> getRandomSpells() {
        return Collections.singletonList(RandomUtils.getRandomItem(Arrays.stream(Spells.values()).filter(spells -> spells.getLevel() == 1).toList()));
    }
}
