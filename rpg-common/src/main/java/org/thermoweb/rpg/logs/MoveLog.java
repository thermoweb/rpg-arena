package org.thermoweb.rpg.logs;

import org.thermoweb.core.data.Pair;

public record MoveLog(String description, Pair<Integer, Integer> from, Pair<Integer, Integer> to) implements ActionLog {
}
