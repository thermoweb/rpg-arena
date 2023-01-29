package org.thermoweb.rpg.encounter.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.thermoweb.core.http.JsonBodyHandler;
import org.thermoweb.rpg.dto.EncounterDto;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Optional;

public class EncounterManagerRestClient implements EncounterManagerClient {

    private static final String ENCOUNTERS_ENDPOINT = "/encounters";
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
            throws IOException, InterruptedException {
        final String uri = prefixUri + ENCOUNTERS_ENDPOINT;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(objectMapper.writeValueAsString(encounterCreationRequest)))
                .build();

        HttpClient httpClient = HttpClient.newHttpClient();

        HttpResponse<EncounterDto> response = httpClient.send(request, new JsonBodyHandler<>(EncounterDto.class));

        return response.body();
    }

    public EncounterDto launch(String id) throws IOException, InterruptedException {
        final String uri = prefixUri + ENCOUNTERS_ENDPOINT + "/" + id + ":launch";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.noBody())
                .build();

        HttpClient httpClient = HttpClient.newHttpClient();

        HttpResponse<EncounterDto> response = httpClient.send(request, new JsonBodyHandler<>(EncounterDto.class));

        return response.body();
    }

    @Override
    public List<EncounterDto> getAll() throws IOException, InterruptedException {
        final String uri = prefixUri + ENCOUNTERS_ENDPOINT;

        return getEncounterList(uri);
    }
    @Override
    public List<EncounterDto> findAllByCharacterId(String id) throws IOException, InterruptedException {
        final String uri = prefixUri + ENCOUNTERS_ENDPOINT + "/search?characterId=" + id;

        return getEncounterList(uri);
    }

    private List<EncounterDto> getEncounterList(String uri) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .GET()
                .build();

        HttpClient httpClient = HttpClient.newHttpClient();

        HttpResponse<EncounterDto[]> response = httpClient.send(request, new JsonBodyHandler<>(EncounterDto[].class));

        return List.of(response.body());
    }

    @Override
    public EncounterDto getById(String id) throws IOException, InterruptedException {
        final String uri = prefixUri + ENCOUNTERS_ENDPOINT + "/" + id;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .GET()
                .build();

        HttpClient httpClient = HttpClient.newHttpClient();

        HttpResponse<EncounterDto> response = httpClient.send(request, new JsonBodyHandler<>(EncounterDto.class));
        return response.body();
    }
}
