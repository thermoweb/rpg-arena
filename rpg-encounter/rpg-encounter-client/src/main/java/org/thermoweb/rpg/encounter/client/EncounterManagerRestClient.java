package org.thermoweb.rpg.encounter.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.thermoweb.core.http.JsonBodyHandler;
import org.thermoweb.rpg.dto.EncounterDto;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Optional;

public class EncounterManagerRestClient implements EncounterManagerClient {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final String prefixUri;

    public EncounterManagerRestClient(String protocol, String host) {
        this(protocol, host, null);
    }

    public EncounterManagerRestClient(String protocol, String host, String port) {
        this.prefixUri = Optional.ofNullable(port)
                .map(p -> protocol + "://" + host + ":" + port)
                .orElseGet(() -> protocol + "://" + host);
    }

    @Override
    public EncounterDto create(EncounterManagerCreationRequest encounterCreationRequest)
            throws URISyntaxException, IOException, InterruptedException {
        final String uri = prefixUri + "/encounters";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(uri))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(objectMapper.writeValueAsString(encounterCreationRequest)))
                .build();

        HttpClient httpClient = HttpClient.newHttpClient();

        HttpResponse<EncounterDto> response = httpClient.send(request, new JsonBodyHandler<>(EncounterDto.class));

        return response.body();
    }
}
