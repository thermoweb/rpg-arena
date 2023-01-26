package org.thermoweb.rpg.front.views;

import lombok.Builder;
import org.thermoweb.rpg.characters.Ability;
import org.thermoweb.rpg.characters.Profiles;
import org.thermoweb.rpg.characters.Species;

@Builder
public record CreateCharacterForm(String name,
                                  Ability strength,
                                  Ability intermediate,
                                  Ability weakness,
                                  Species species,
                                  Profiles profile) {
}
