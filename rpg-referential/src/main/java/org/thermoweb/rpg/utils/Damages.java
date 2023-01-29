package org.thermoweb.rpg.utils;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
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
        return result + bonus;
    }

    public DamagesLog getLoggedDamages() {
        List<Integer> rolls = new ArrayList<>();
        for (int i = 0; i < number; i++) {
            rolls.add(DiceRoller.roll(dice.getFaces()));
        }

        return DamagesLog.builder()
                .damages(this.toString())
                .rolls(rolls)
                .total(rolls.stream().mapToInt(Integer::valueOf).sum() + bonus)
                .build();
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

    public int averageDamage() {
        int min = number + bonus;
        int max = number * dice.getFaces() + bonus;

        return (min + max) / 2;
    }

    @Override
    public String toString() {
        return String.format("%dd%d%+d", number, dice.getFaces(), bonus).replace("+0", "");
    }

    @Builder
    public record DamagesLog(int total, List<Integer> rolls, String damages) {

        @Override
        public String toString() {
            return String.format("%d (%s)", total, damages);
        }
    }
}
