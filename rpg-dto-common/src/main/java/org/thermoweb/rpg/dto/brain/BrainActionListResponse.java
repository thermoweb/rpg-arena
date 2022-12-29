package org.thermoweb.rpg.dto.brain;

import lombok.Builder;
import org.thermoweb.rpg.dto.action.ActionDto;

import java.util.List;

@Builder
public record BrainActionListResponse(List<ActionDto> action) {
}
