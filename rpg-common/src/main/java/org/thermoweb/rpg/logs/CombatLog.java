package org.thermoweb.rpg.logs;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
public record CombatLog(List<RoundLog> logs, CharacterLog winner) {

}
