package org.thermoweb.rpg.encounter.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.thermoweb.rpg.dto.EncounterDto;

import java.io.IOException;
import java.net.URISyntaxException;

public interface EncounterManagerClient {

    EncounterDto create(EncounterManagerCreationRequest encounterCreationRequest) throws URISyntaxException, IOException, InterruptedException;

}
