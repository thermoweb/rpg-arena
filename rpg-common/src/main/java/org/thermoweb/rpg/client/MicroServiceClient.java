package org.thermoweb.rpg.client;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Objects;
import java.util.Optional;

public abstract class MicroServiceClient {


    protected final String prefixUri;
    protected final ObjectMapper objectMapper = new ObjectMapper();

    protected MicroServiceClient(MicroServiceProperties properties) {
        this.prefixUri = Optional.ofNullable(properties)
                .map(MicroServiceProperties::getPrefixUri)
                .orElseGet(() -> getPrefixFromProperties(properties));
    }

    private static String getPrefixFromProperties(MicroServiceProperties properties) {
        Objects.requireNonNull(properties);
        return String.format("%s://%s%s",
                Optional.ofNullable(properties.getProtocol()).orElse("http"),
                Optional.ofNullable(properties.getHost()).orElse("localhost"),
                Optional.ofNullable(properties.getPort()).map(p -> ":" + p).orElse("")
        );
    }
}
