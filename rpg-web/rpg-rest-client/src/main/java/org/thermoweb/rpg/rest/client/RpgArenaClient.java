package org.thermoweb.rpg.rest.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.thermoweb.rpg.dto.CharacterDto;
import org.thermoweb.rpg.dto.EncounterDto;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

public interface RpgArenaClient {

    CharacterDto createCharacter(CharacterCreationRequest request) throws IOException, URISyntaxException, InterruptedException;

    EncounterDto createRandomEncounter(EncounterCreationRequest request) throws IOException, InterruptedException;
    List<CharacterDto> getAll() throws IOException, InterruptedException;
    Optional<CharacterDto> getById(String id) throws IOException, InterruptedException;
}
