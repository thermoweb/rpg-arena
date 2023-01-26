package org.thermoweb.rpg.logs;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS)
public sealed interface ActionLog permits AttackLog, MoveLog, SpellLog {

    String toString();
    enum Status {
        SUCCESS,
        FAILED
    }
}
