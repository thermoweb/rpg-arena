package org.thermoweb.rpg.actions;

import lombok.Getter;
import org.thermoweb.rpg.utils.Damages;

@Getter
public enum Spells {
    FROST_BOLT(18, 1, "1d4+1", 0),
    FIRE_BOLT(18, 1, "1d8", 1),
    MAGIC_MISSILE(18, 1, "3d4", 0),
    FIREBALL(18, 2, "8d6", 2);

    private final int range;
    private final int level;
    private final Damages damages;
    private final int hpCost;

    Spells(int range, int level, String damages, int hpCost) {
        this.range = range;
        this.level = level;
        this.damages = Damages.of(damages);
        this.hpCost = hpCost;
    }

    public int getDamages() {
        return damages.get();
    }
}
