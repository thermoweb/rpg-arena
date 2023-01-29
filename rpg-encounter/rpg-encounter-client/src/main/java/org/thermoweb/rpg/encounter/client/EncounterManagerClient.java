package org.thermoweb.rpg.encounter.client;

import org.thermoweb.rpg.dto.EncounterDto;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

public interface EncounterManagerClient {
    String ENCOUNTERS_ENDPOINT = "/encounters";
    EncounterDto create(EncounterManagerCreationRequest encounterCreationRequest) throws URISyntaxException, IOException, InterruptedException;

    List<EncounterDto> getAll() throws IOException, InterruptedException;

    List<EncounterDto> findAllByCharacterId(String id) throws IOException, InterruptedException;

    EncounterDto getById(String id) throws IOException, InterruptedException;

    EncounterDto launch(String id) throws IOException, InterruptedException;
}
