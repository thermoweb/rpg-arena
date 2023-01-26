package org.thermoweb.rpg.actions;

import org.thermoweb.rpg.characters.DefaultCharacter;

public sealed interface TargetableAction extends Action permits Attack, CastSpell {
    void setTarget(DefaultCharacter character);
    DefaultCharacter getTarget();
}
