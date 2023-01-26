package org.thermoweb.rpg.logs;

import lombok.Builder;

@Builder
public record CharacterLog(String name, String id, Integer hitPoints) {
}
