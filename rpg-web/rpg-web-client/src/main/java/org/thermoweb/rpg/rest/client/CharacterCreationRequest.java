package org.thermoweb.rpg.rest.client;

import lombok.Builder;
import org.thermoweb.rpg.characters.Profiles;
import org.thermoweb.rpg.characters.Species;
import org.thermoweb.rpg.characters.Statistics;

@Builder
public record CharacterCreationRequest(String name,
                                       Statistics.StartStatistics statistics,
                                       Species species,
                                       Profiles profile) {

}
