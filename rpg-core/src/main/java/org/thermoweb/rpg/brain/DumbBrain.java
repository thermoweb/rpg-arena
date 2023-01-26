package org.thermoweb.rpg.brain;

import org.thermoweb.core.utils.RandomUtils;
import org.thermoweb.rpg.actions.Action;
import org.thermoweb.rpg.actions.Attack;
import org.thermoweb.rpg.actions.CastSpell;
import org.thermoweb.rpg.actions.Direction;
import org.thermoweb.rpg.actions.Move;
import org.thermoweb.rpg.characters.DefaultCharacter;
import org.thermoweb.rpg.characters.Skills;
import org.thermoweb.rpg.environment.Arena;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.IntStream;

public class DumbBrain implements Brain {
    @Override
    public List<Action> getActions(DefaultCharacter defaultCharacter, Arena arena) {
        return arena.getCharacters().stream()
                .filter(c -> !Objects.equals(c.getId(), defaultCharacter.getId()))
                .findFirst()
                .map(target -> getActionList(defaultCharacter, target))
                .orElseGet(Collections::emptyList);
    }

    private List<Action> getActionList(DefaultCharacter from, DefaultCharacter target) {
        List<Action> actions = new ArrayList<>();
        actions.add(getRandomAttack(from, target));
        IntStream.range(0, from.getSpecies().getSpeed())
                .forEach(n -> actions
                        .add(Move.builder()
                                .direction(Direction.getRandomDirection())
                                .build()));
        return actions;
    }

    private static Action getRandomAttack(DefaultCharacter defaultCharacter, DefaultCharacter target) {
        List<Action> possibleActions = new ArrayList<>();
        possibleActions.add(Attack.builder()
                .weapon(defaultCharacter.getEquipmentSlots().getWeapon())
                .target(target).build());
        boolean isSpellCasting = Optional.ofNullable(defaultCharacter.getSkills())
                .orElseGet(Collections::emptySet)
                .stream()
                .anyMatch(Skills.SPELL_CASTING::equals);
        boolean doestNotHaveSpell = Optional.ofNullable(defaultCharacter.getSpellbook())
                .map(List::isEmpty)
                .orElse(true);
        if (isSpellCasting && !doestNotHaveSpell) {
            possibleActions.add(CastSpell.builder()
                    .from(defaultCharacter)
                    .target(target)
                    .spell(RandomUtils.getRandomItem(defaultCharacter.getSpellbook()))
                    .build());
        }
        return RandomUtils.getRandomItem(possibleActions);
    }
}
