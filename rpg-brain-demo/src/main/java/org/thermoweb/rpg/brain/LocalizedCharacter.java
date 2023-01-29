package org.thermoweb.rpg.brain;

import lombok.Builder;
import org.thermoweb.core.data.Pair;
import org.thermoweb.rpg.characters.DefaultCharacter;

@Builder
public record LocalizedCharacter(DefaultCharacter character, Pair<Integer, Integer> position) {

}
