package org.thermoweb.rpg.utils;

import org.thermoweb.core.data.Pair;
import org.thermoweb.rpg.arena.Grid;
import org.thermoweb.rpg.characters.DefaultCharacter;
import org.thermoweb.rpg.environment.Arena;

import java.util.Optional;
import java.util.random.RandomGenerator;

import static java.lang.Math.abs;
import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

public class GridUtils {
    private static final RandomGenerator generator = RandomGenerator.getDefault();

    private GridUtils() {
    }

    public static Pair<Integer, Integer> getRandomCoords(Grid grid) {
        return new Pair<>(generator.nextInt(grid.getX() - 1), generator.nextInt(grid.getY() - 1));
    }

    public static void consolePrint(Arena arena) {
        for (int y = 0; y < arena.getGrid().length; y++) {
            for (int x = 0; x < arena.getGrid()[y].length; x++) {
                String cellContent = Optional.ofNullable(arena.getGrid()[y][x])
                        .map(DefaultCharacter::getName)
                        .map(n -> "[" + n.charAt(0) + "]")
                        .orElse("[ ]");
                System.out.print(cellContent);
            }
            System.out.println();
        }
    }

    public static int getDistance(Pair<Integer, Integer> from, Pair<Integer, Integer> to) {
        return abs(to.getLeft() - from.getLeft()) + abs(to.getRight() - from.getRight());
    }

    public static double getDirectDistance(Pair<Integer, Integer> from, Pair<Integer, Integer> to) {
        return sqrt(pow(abs(to.getLeft() - from.getLeft()), 2) + pow(abs(to.getRight() - from.getRight()), 2));
    }
}
