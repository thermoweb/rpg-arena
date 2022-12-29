package org.thermoweb.rpg.brain;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import org.thermoweb.core.http.JsonBodyHandler;
import org.thermoweb.rpg.actions.Action;
import org.thermoweb.rpg.characters.BrainType;
import org.thermoweb.rpg.characters.DefaultCharacter;
import org.thermoweb.rpg.dto.brain.BrainActionListRequest;
import org.thermoweb.rpg.dto.brain.BrainActionListResponse;
import org.thermoweb.rpg.environment.Arena;
import org.thermoweb.rpg.mapper.ActionMapper;
import org.thermoweb.rpg.mapper.ArenaDtoMapper;
import org.thermoweb.rpg.mapper.CharacterDtoMapper;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

@Getter
public class RemoteBrain implements Brain {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final String uri;

    public RemoteBrain(String prefixUri) {
        this.uri = prefixUri;
    }

    @Override
    public List<Action> getActions(DefaultCharacter defaultCharacter, Arena arena) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(uri + "/actions"))
                    .header("Content-Type", "application/json")
                    .header("accept", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(objectMapper
                            .writeValueAsString(new BrainActionListRequest(
                                    CharacterDtoMapper.map(defaultCharacter),
                                    ArenaDtoMapper.map(arena)))))
                    .build();

            HttpClient httpClient = HttpClient.newHttpClient();

            HttpResponse<BrainActionListResponse> response = httpClient.send(request, new JsonBodyHandler<>(BrainActionListResponse.class));

            return response.body().action().stream().map(ActionMapper::map).toList();
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public BrainType getType() {
        return BrainType.REMOTE;
    }
}
