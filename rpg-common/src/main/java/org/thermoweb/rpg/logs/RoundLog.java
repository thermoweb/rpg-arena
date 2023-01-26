package org.thermoweb.rpg.logs;

import lombok.Builder;

import java.util.List;
import java.util.Map;

@Builder
public record RoundLog(Integer round, Map<String, List<ActionLog>> logs, List<CharacterLog> characters) {
}
