package org.thermoweb.rpg.utils;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Getter
@Setter
@Builder
public class Damages {

    private static final String DICE_REGEX = "([0-9]+)d([0-9]+)([+|-][0-9]+)?";
    private static final Pattern DICE_PATTERN = Pattern.compile(DICE_REGEX);

    private final int number;
    private final Dice dice;
    private final int bonus;

    public int get() {
        int result = 0;
        for (int i = 0; i < number; i++) {
            result += DiceRoller.roll(dice.getFaces());
        }
        return result;
    }

    public static Damages of(String damages) {
        Matcher matcher = DICE_PATTERN.matcher(damages);
        if (!matcher.find()) {
            throw new IllegalArgumentException("wrong damages declaration");
        }
        return Damages.builder()
                .number(Integer.parseInt(matcher.group(1)))
                .dice(Dice.valueOf("D" + matcher.group(2)))
                .bonus(Optional.ofNullable(matcher.group(3)).map(Integer::parseInt).orElse(0))
                .build();
    }
}
