package org.thermoweb.rpg.rest.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.thermoweb.rpg.characters.CharacterService;
import org.thermoweb.rpg.data.characters.CharacterEntity;
import org.thermoweb.rpg.dto.EncounterDto;
import org.thermoweb.rpg.encounter.client.EncounterManagerClient;
import org.thermoweb.rpg.encounter.client.EncounterManagerCreationRequest;
import org.thermoweb.rpg.encounter.client.EncounterManagerRestClient;
import org.thermoweb.rpg.rest.client.EncounterCreationRequest;
import org.thermoweb.rpg.rest.mapper.CharacterMapper;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@RestController
@RequestMapping("/encounters")
public class EncounterController {

    private final CharacterService characterService;

    public EncounterController(CharacterService characterService) {
        this.characterService = characterService;
    }

    @PostMapping
    public EncounterDto createEncounter(@RequestBody EncounterCreationRequest encounterCreationRequest) {
        CharacterEntity player = characterService.getById(encounterCreationRequest.characterId()).orElseThrow();
        CharacterEntity bbeg = Optional.ofNullable(encounterCreationRequest.opponentId())
                .map(characterService::getById)
                .flatMap(Function.identity())
                .orElseGet(() -> characterService.getRandomExcept(player));
        EncounterManagerClient client = new EncounterManagerRestClient("http", "localhost", "8082");

        EncounterManagerCreationRequest request = EncounterManagerCreationRequest.builder()
                .participants(
                        List.of(CharacterMapper.map(player)
                                        .toBuilder()
                                        .brain(encounterCreationRequest.brain())
                                        .build(),
                                CharacterMapper.map(bbeg)))
                .grid(encounterCreationRequest.grid())
                .build();
        try {
            return client.create(request);
        } catch (URISyntaxException | InterruptedException | IOException e) {
            throw new RuntimeException(e);
        }
    }

}
