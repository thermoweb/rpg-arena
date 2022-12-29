package org.thermoweb.rpg.data.encounters;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.thermoweb.rpg.encounters.EncounterStatus;

import java.util.Optional;

public interface EncounterRepository extends MongoRepository<EncounterEntity, String> {

    Optional<EncounterEntity> findFirstByStatus(EncounterStatus status);
}
