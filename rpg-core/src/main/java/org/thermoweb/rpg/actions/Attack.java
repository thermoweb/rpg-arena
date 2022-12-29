package org.thermoweb.rpg.actions;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.thermoweb.rpg.characters.DefaultCharacter;
import org.thermoweb.rpg.environment.Arena;
import org.thermoweb.rpg.equipment.Weapon;
import org.thermoweb.rpg.utils.GridUtils;

import java.util.Objects;

@Builder
@Getter
@Slf4j
public final class Attack implements Action {

    private final Weapon weapon;
    private final DefaultCharacter target;
    private DefaultCharacter from;

    @Override
    public String execute(Arena arena) throws ActionException {
        try {
            autoCheck();
        } catch (NullPointerException e) {
            throw new ActionException("Action is not valid", e);
        }
        if (GridUtils.getDirectDistance(arena.getCharacterPairMap().get(from.getId()), arena.getCharacterPairMap().get(target.getId())) > weapon.getRange()) {
            throw new ActionException("target is too far to attack.");
        }

        if (from.rollAbility(weapon.getAbility())) {
            int damages = weapon.getDamages();
            return target.takeDamage(damages);
        } else {
            String actionLog = "attacks missed...";
            log.info(actionLog);
            return actionLog;
        }
    }

    @Override
    public void setOwner(DefaultCharacter owner) {
        this.from = owner;
    }

    private void autoCheck() throws ActionException {
        try {
            Objects.requireNonNull(weapon, "'weapon' should be not null");
            Objects.requireNonNull(from, "'from' should be not null");
            Objects.requireNonNull(target, "'target' should be not null");
        } catch (NullPointerException e) {
            throw new ActionException("Action is not valid", e);
        }
    }
}
