package org.thermoweb.rpg.rest.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
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
import org.thermoweb.rpg.rest.config.RpgArenaWebProperties;
import org.thermoweb.rpg.rest.mapper.CharacterMapper;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@CrossOrigin
@RestController
@RequestMapping("/encounters")
public class EncounterController {

    private final EncounterManagerClient encounterManagerClient;
    private final CharacterService characterService;

    public EncounterController(CharacterService characterService, RpgArenaWebProperties rpgArenaWebProperties) {
        this.encounterManagerClient = new EncounterManagerRestClient(rpgArenaWebProperties.getEncounterManager());
        this.characterService = characterService;
    }

    @PostMapping
    public EncounterDto createEncounter(@RequestBody EncounterCreationRequest encounterCreationRequest) {
        CharacterEntity player = Optional.ofNullable(encounterCreationRequest.characterId())
                .filter(s -> !s.isEmpty())
                .map(characterService::getById)
                .orElseGet(() -> characterService.getRandom().stream().findFirst())
                .orElseThrow();
        CharacterEntity bbeg = Optional.ofNullable(encounterCreationRequest.opponentId())
                .map(characterService::getById)
                .flatMap(Function.identity())
                .orElseGet(() -> characterService.getRandomExcept(player));

        EncounterManagerCreationRequest request = EncounterManagerCreationRequest.builder()
                .participants(
                        List.of(CharacterMapper.map(player)
                                        .toBuilder()
                                        .brain(encounterCreationRequest.brain())
                                        .build(),
                                CharacterMapper.map(bbeg)
                                        .toBuilder()
                                        .brain(encounterCreationRequest.opponentBrain())
                                        .build()))
                .grid(encounterCreationRequest.grid())
                .build();
        try {
            return encounterManagerClient.create(request);
        } catch (URISyntaxException | InterruptedException | IOException e) {
            throw new RuntimeException(e);
        }
    }

}
