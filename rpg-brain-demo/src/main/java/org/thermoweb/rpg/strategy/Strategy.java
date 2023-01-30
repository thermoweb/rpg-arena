package org.thermoweb.rpg.strategy;

import org.thermoweb.rpg.actions.Action;
import org.thermoweb.rpg.brain.LocalizedCharacter;
import org.thermoweb.rpg.environment.Arena;

import java.util.List;

public interface Strategy {
    List<Action> getActions(LocalizedCharacter self, Arena arena);
}
