package org.thermoweb.rpg.data.encounters;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.thermoweb.rpg.arena.Grid;
import org.thermoweb.rpg.data.characters.CharacterEntity;
import org.thermoweb.rpg.encounters.EncounterStatus;

import java.util.List;

@Getter
@Setter
@Builder
public class EncounterEntity {

    @Id
    private String id;
    private EncounterStatus status;
    private Grid grid;
    private List<CharacterEntity> characters;
    private List<String> combatLog;
}
