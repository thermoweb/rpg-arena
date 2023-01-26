package org.thermoweb.rpg.actions;

import org.thermoweb.rpg.characters.DefaultCharacter;
import org.thermoweb.rpg.environment.Arena;
import org.thermoweb.rpg.logs.ActionLog;

public sealed interface Action permits Move, TargetableAction {
    ActionLog execute(Arena arena) throws ActionException;

    void setOwner(DefaultCharacter owner);
}
