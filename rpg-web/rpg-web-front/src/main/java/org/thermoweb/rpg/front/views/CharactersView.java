package org.thermoweb.rpg.front.views;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.thermoweb.rpg.arena.Grid;
import org.thermoweb.rpg.characters.Ability;
import org.thermoweb.rpg.characters.BrainType;
import org.thermoweb.rpg.characters.Profiles;
import org.thermoweb.rpg.characters.Species;
import org.thermoweb.rpg.characters.Statistics;
import org.thermoweb.rpg.dto.CharacterDto;
import org.thermoweb.rpg.dto.EncounterDto;
import org.thermoweb.rpg.dto.brain.BrainDto;
import org.thermoweb.rpg.rest.client.CharacterCreationRequest;
import org.thermoweb.rpg.rest.client.EncounterCreationRequest;
import org.thermoweb.rpg.rest.client.RpgArenaClient;
import org.thermoweb.rpg.rest.client.RpgArenaRestClient;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

@Controller
@RequestMapping("/ui/characters")
public class CharactersView {

    private final RpgArenaClient rpgArenaClient = new RpgArenaRestClient("http", "localhost", "8080");

    @GetMapping
    public String characters(Model model) {
        try {
            List<CharacterDto> response = rpgArenaClient.getAll();
            model.addAttribute("characters", response);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        return "characters_list";
    }

    @GetMapping("create")
    public String create(Model model) {
        model.addAttribute("species", Species.values());
        model.addAttribute("profiles", Profiles.values());
        model.addAttribute("abilities", Ability.values());
        model.addAttribute("createRequest", CreateCharacterForm.builder().build());

        return "characters_create";
    }

    @PostMapping("create")
    public String create(@ModelAttribute CreateCharacterForm request) {
        try {
            CharacterDto character = rpgArenaClient.createCharacter(CharacterCreationRequest.builder()
                            .name(request.name())
                            .statistics(Statistics.StartStatistics.builder()
                                    .strength(request.strength())
                                    .intermediate(request.intermediate())
                                    .weakness(request.weakness())
                                    .build())
                            .species(request.species())
                            .profile(request.profile())
                    .build());
            return "redirect:/ui/characters/" + character.id();
        } catch (IOException | URISyntaxException | InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    @GetMapping("{id}")
    public String getEncounter(@PathVariable String id, Model model) {
        try {
            CharacterDto character = rpgArenaClient.getById(id).orElseThrow();
            model.addAttribute("character", character);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        return "characters_view";
    }

    @GetMapping("{id}:random")
    public String createRandomEncounter(@PathVariable String id) {
        try {
            EncounterDto encounter = rpgArenaClient.createRandomEncounter(EncounterCreationRequest.builder()
                    .characterId(id)
                    .brain(new BrainDto(BrainType.REMOTE, "http://localhost:8083/brain"))
                    .grid(Grid.SQUARE_16)
                    .build());

            return "redirect:/ui/encounters/" + encounter.id();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
