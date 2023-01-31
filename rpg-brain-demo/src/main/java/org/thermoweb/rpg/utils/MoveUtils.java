package org.thermoweb.rpg.utils;

import org.thermoweb.rpg.actions.Direction;
import org.thermoweb.rpg.actions.Move;
import org.thermoweb.rpg.brain.LocalizedCharacter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MoveUtils {

    private MoveUtils() {

    }

    public static List<Move> getCloser(LocalizedCharacter me, LocalizedCharacter target) {
        List<Move> moves = new ArrayList<>();

        int verticalDistance = me.position().getLeft() - target.position().getLeft();
        int horizontalDistance = me.position().getRight() - target.position().getRight();
        if (verticalDistance != 0) {
            IntStream.range(0, Math.abs(verticalDistance))
                    .forEach(n -> moves.add(Move.builder()
                            .direction(verticalDistance > 0 ? Direction.UP : Direction.DOWN)
                            .build()));
        }

        if (horizontalDistance != 0) {
            IntStream.range(0, Math.abs(horizontalDistance))
                    .forEach(n -> moves.add(Move.builder()
                            .direction(horizontalDistance > 0 ? Direction.LEFT : Direction.RIGHT)
                            .build()));
        }

        return moves.stream()
                .limit(me.character().getSpecies().getSpeed())
                .collect(Collectors.toList());
    }
}
