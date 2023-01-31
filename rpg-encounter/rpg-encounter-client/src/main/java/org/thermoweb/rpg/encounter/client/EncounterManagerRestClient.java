package org.thermoweb.rpg.encounter.client;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.thermoweb.core.http.JsonBodyHandler;
import org.thermoweb.rpg.client.MicroServiceClient;
import org.thermoweb.rpg.client.MicroServiceProperties;
import org.thermoweb.rpg.dto.EncounterDto;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Optional;

public class EncounterManagerRestClient extends MicroServiceClient implements EncounterManagerClient {

    public EncounterManagerRestClient(MicroServiceProperties properties) {
        super(properties);
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
    public Page<EncounterDto> getAll() throws IOException, InterruptedException {
        final String uri = prefixUri + ENCOUNTERS_ENDPOINT;

        return getEncounterPage(uri);
    }

    @Override
    public Page<EncounterDto> getAll(Integer page, Integer size) throws IOException, InterruptedException {
        String uri = prefixUri + ENCOUNTERS_ENDPOINT
                + "?page=" + Optional.ofNullable(page).orElse(0)
                + "&size=" + Optional.ofNullable(size).orElse(10);

        return getEncounterPage(uri);
    }

    @Override
    public List<EncounterDto> findAllByCharacterId(String id) throws IOException, InterruptedException {
        final String uri = prefixUri + ENCOUNTERS_ENDPOINT + "/search?characterId=" + id;

        return getEncounterList(uri);
    }

    private Page<EncounterDto> getEncounterPage(String uri) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .GET()
                .build();

        HttpClient httpClient = HttpClient.newHttpClient();

        HttpResponse<EncounterPage> response = httpClient.send(request, new JsonBodyHandler<>(EncounterPage.class));

        return response.body();
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

    static class EncounterPage extends PageImpl<EncounterDto> {

        @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
        public EncounterPage(@JsonProperty("content") List<EncounterDto> content,
                             @JsonProperty("number") int number,
                             @JsonProperty("size") int size,
                             @JsonProperty("totalElements") Long totalElements,
                             @JsonProperty("pageable") JsonNode pageable,
                             @JsonProperty("last") boolean last,
                             @JsonProperty("totalPages") int totalPages,
                             @JsonProperty("sort") JsonNode sort,
                             @JsonProperty("first") boolean first,
                             @JsonProperty("numberOfElements") int numberOfElements,
                             @JsonProperty("empty") boolean empty) {
            super(content, PageRequest.of(number, size), totalElements);
        }
    }
}
