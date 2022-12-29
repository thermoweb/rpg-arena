package org.thermoweb.rpg.dto.action;

import java.util.Map;

public record ActionDto(ActionType type, Map<String, Object> parameters) {

}
