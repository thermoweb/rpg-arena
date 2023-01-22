package org.thermoweb.rpg.actions;

import lombok.Getter;
import org.thermoweb.rpg.utils.Damages;
import org.thermoweb.rpg.utils.Dice;

@Getter
public enum Spells {
    FIREBALL(18, Dice.D8, 2),
    FROSTBOLT(18, Dice.D6, 1);

    private final int range;
    private final Damages damages;
    private final int hpCost;

    Spells(int range, Dice dice, int hpCost) {
        this.range = range;
        this.damages = Damages.builder().dice(dice).build();
        this.hpCost = hpCost;
    }

    public int getDamages() {
        return damages.get();
    }
}
