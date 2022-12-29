package org.thermoweb.rpg.actions;

import org.thermoweb.rpg.characters.DefaultCharacter;
import org.thermoweb.rpg.environment.Arena;

public sealed interface Action permits Attack, Move {
    String execute(Arena arena) throws ActionException;
    void setOwner(DefaultCharacter owner);
}