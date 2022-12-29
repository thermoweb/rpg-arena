package org.thermoweb.rpg.brain;

import org.thermoweb.rpg.actions.Action;
import org.thermoweb.rpg.characters.BrainType;
import org.thermoweb.rpg.characters.DefaultCharacter;
import org.thermoweb.rpg.environment.Arena;

import java.util.List;

public interface Brain {
    List<Action> getActions(DefaultCharacter defaultCharacter, Arena arena);
    default BrainType getType() {
        return BrainType.LOCAL;
    }
}
