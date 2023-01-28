package org.thermoweb.rpg.logs;

import lombok.Builder;
import org.thermoweb.core.data.Pair;

@Builder
public record MoveLog(String description, Pair<Integer, Integer> from, Pair<Integer, Integer> to) implements ActionLog {

    @Override
    public String toString() {
        return description;
    }
}
