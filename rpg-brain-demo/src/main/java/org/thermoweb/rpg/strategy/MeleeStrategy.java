package org.thermoweb.rpg.strategy;

import org.thermoweb.rpg.actions.Action;
import org.thermoweb.rpg.actions.Move;
import org.thermoweb.rpg.actions.TargetableAction;
import org.thermoweb.rpg.brain.DumbBrain;
import org.thermoweb.rpg.brain.LocalizedCharacter;
import org.thermoweb.rpg.characters.DefaultCharacter;
import org.thermoweb.rpg.environment.Arena;
import org.thermoweb.rpg.utils.MoveUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class MeleeStrategy implements Strategy {

    public List<Action> getActions(LocalizedCharacter self, Arena arena) {
        DefaultCharacter target = arena.getCharacters().stream()
                .filter(character -> !Objects.equals(character.getId(), self.character().getId()))
                .findFirst()
                .orElseThrow();
        List<Move> moves = MoveUtils.getCloser(
                self,
                LocalizedCharacter.builder()
                        .character(target)
                        .position(arena.getCharacterPairMap().get(target.getId()))
                        .build());

        List<TargetableAction> possibleActions = DumbBrain.getPossibleActions(self.character(), target);
        possibleActions.sort(Comparator.comparingInt(o -> o.getDamages().averageDamage()));

        List<Action> actions = new ArrayList<>(moves);
        actions.add(possibleActions.get(0));

        return actions;
    }


}
