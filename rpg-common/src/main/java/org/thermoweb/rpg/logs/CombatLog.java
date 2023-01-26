package org.thermoweb.rpg.logs;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class CombatLog {

    private final List<RoundLog> logs;
    private final CharacterLog winner;

}
