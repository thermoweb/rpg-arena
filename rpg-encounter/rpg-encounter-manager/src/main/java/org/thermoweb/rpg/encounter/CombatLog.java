package org.thermoweb.rpg.encounter;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class CombatLog {

    private final List<String> logs;

}
