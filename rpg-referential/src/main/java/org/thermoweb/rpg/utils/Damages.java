package org.thermoweb.rpg.utils;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.thermoweb.rpg.utils.Dice;

@Getter
@Setter
@Builder
public class Damages {
    private final Dice dice;
    private final int bonus;

    public int get() {
        return dice.roll() + bonus;
    }
}
