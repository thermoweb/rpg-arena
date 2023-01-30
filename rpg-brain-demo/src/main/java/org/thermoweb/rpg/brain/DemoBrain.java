package org.thermoweb.rpg.brain;

import org.thermoweb.rpg.actions.Action;
import org.thermoweb.rpg.actions.Direction;
import org.thermoweb.rpg.actions.Move;
import org.thermoweb.rpg.actions.Spells;
import org.thermoweb.rpg.characters.BrainType;
import org.thermoweb.rpg.characters.DefaultCharacter;
import org.thermoweb.rpg.characters.Profiles;
import org.thermoweb.rpg.environment.Arena;
import org.thermoweb.rpg.strategy.MeleeStrategy;
import org.thermoweb.rpg.strategy.Strategy;
import org.thermoweb.rpg.utils.GridUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class DemoBrain implements Brain {
    @Override
    public List<Action> getActions(DefaultCharacter defaultCharacter, Arena arena) {
        return arena.getCharacters().stream()
                .filter(c -> !Objects.equals(c.getId(), defaultCharacter.getId()))
                .findFirst()
                .map(target -> getActionList(defaultCharacter, target, arena))
                .orElseGet(Collections::emptyList);
    }

    private List<Action> getActionList(DefaultCharacter self, DefaultCharacter target, Arena arena) {
        LocalizedCharacter me = LocalizedCharacter.builder()
                .character(self)
                .position(arena.getCharacterPairMap().get(self.getId()))
                .build();

        if (self.getProfiles().equals(Profiles.FIGHTER)) {
            Strategy strategy = new MeleeStrategy();
            return strategy.getActions(me, arena);
        }

        LocalizedCharacter ennemi = LocalizedCharacter.builder()
                .character(target)
                .position(arena.getCharacterPairMap().get(target.getId()))
                .build();

        List<Move> moveList = getAtRange(me, ennemi);

        List<Action> actions = new ArrayList<>(moveList);
        actions.add(DumbBrain.getRandomAttack(self, target));

        return actions;
    }

    private List<Move> getAtRange(LocalizedCharacter me, LocalizedCharacter target) {
        List<Move> moves = new ArrayList<>();

        int verticalDistance = me.position().getLeft() - target.position().getLeft();
        int horizontalDistance = me.position().getRight() - target.position().getRight();

        int maxRange = getMaxRange(me);
        double currentDistance = GridUtils.getDirectDistance(me.position(), target.position());
        if (currentDistance < maxRange) {
            return Collections.emptyList();
        }

        if (verticalDistance != maxRange) {
            int distanceToMove = (int) Math.round(currentDistance - maxRange);
            IntStream.range(0, Math.abs(verticalDistance) - distanceToMove + 1)
                    .forEach(n -> moves.add(Move.builder()
                            .direction(verticalDistance > 0 ? Direction.UP : Direction.DOWN)
                            .build()));
        }

        if (horizontalDistance != maxRange) {
            IntStream.range(0, Math.abs(horizontalDistance) - maxRange + 1)
                    .forEach(n -> moves.add(Move.builder()
                            .direction(horizontalDistance > 0 ? Direction.LEFT : Direction.RIGHT)
                            .build()));
        }

        return moves.stream()
                .limit(me.character().getSpecies().getSpeed())
                .collect(Collectors.toList());
    }

    private Integer getMaxRange(LocalizedCharacter me) {
        Integer spellMaxRange = me.character().getSpellbook().stream()
                .map(Spells::getRange)
                .max(Integer::compareTo)
                .orElse(0);

        return Math.max(spellMaxRange, me.character().getEquipmentSlots().getWeapon().getRange());
    }

    @Override
    public BrainType getType() {
        return BrainType.REMOTE;
    }
}
