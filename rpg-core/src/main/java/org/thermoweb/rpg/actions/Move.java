package org.thermoweb.rpg.actions;

import lombok.Builder;
import lombok.Getter;
import org.thermoweb.core.data.Pair;
import org.thermoweb.rpg.characters.DefaultCharacter;
import org.thermoweb.rpg.environment.Arena;
import org.thermoweb.rpg.logs.ActionLog;
import org.thermoweb.rpg.logs.MoveLog;
import org.thermoweb.rpg.utils.GridUtils;

@Builder
@Getter
public final class Move implements Action {

    private final Direction direction;
    private DefaultCharacter owner;

    @Override
    public ActionLog execute(Arena arena) throws ActionException {
        Pair<Integer, Integer> coords = arena.getCharacterPairMap().get(owner.getId());
        int y = coords.getLeft();
        int x = coords.getRight();
        switch (direction) {
            case UP -> y -= 1;
            case DOWN -> y += 1;
            case LEFT -> x -= 1;
            case RIGHT -> x += 1;
        }
        try {
            Pair<Integer, Integer> newPosition = new Pair<>(y, x);
            String actionLog = String.format("%s moving %s from %s to %s", owner.getName(), direction, coords, newPosition);
            arena.move(owner, newPosition);
            GridUtils.consolePrint(arena);
            return new MoveLog(actionLog, coords, newPosition);
        } catch (IllegalArgumentException e) {
            throw new ActionException(e.getMessage(), e);
        }
    }

    @Override
    public void setOwner(DefaultCharacter owner) {
        this.owner = owner;
    }
}
