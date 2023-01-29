package org.thermoweb.rpg.rest.client;

import org.thermoweb.core.http.JsonBodyHandler;
import org.thermoweb.rpg.client.MicroServiceClient;
import org.thermoweb.rpg.client.MicroServiceProperties;
import org.thermoweb.rpg.dto.CharacterDto;
import org.thermoweb.rpg.dto.EncounterDto;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class RpgArenaRestClient extends MicroServiceClient implements RpgArenaClient {

    public RpgArenaRestClient(MicroServiceProperties properties) {
        super(properties);
    }

    @Override
    public CharacterDto createCharacter(CharacterCreationRequest characterCreationRequest)
            throws IOException, InterruptedException {
        final String uri = prefixUri + CHARACTERS_ENDPOINT;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(objectMapper.writeValueAsString(characterCreationRequest)))
                .build();

        HttpClient httpClient = HttpClient.newHttpClient();

        HttpResponse<CharacterDto> response = httpClient.send(request, new JsonBodyHandler<>(CharacterDto.class));

        return response.body();
    }

    @Override
    public EncounterDto createRandomEncounter(EncounterCreationRequest encounterCreationRequest)
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

    @Override
    public List<CharacterDto> getAll()
            throws IOException, InterruptedException {
        final String uri = prefixUri + CHARACTERS_ENDPOINT;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .GET()
                .build();
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpResponse<CharacterDto[]> response = httpClient.send(request, new JsonBodyHandler<>(CharacterDto[].class));

        return Arrays.stream(response.body()).toList();
    }

    @Override
    public Optional<CharacterDto> getById(String id) throws IOException, InterruptedException {
        final String uri = prefixUri + CHARACTERS_ENDPOINT + "/" + id;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .GET()
                .build();

        HttpClient httpClient = HttpClient.newHttpClient();

        HttpResponse<CharacterDto> response = httpClient.send(request, new JsonBodyHandler<>(CharacterDto.class));
        return Optional.ofNullable(response.body());
    }
}
