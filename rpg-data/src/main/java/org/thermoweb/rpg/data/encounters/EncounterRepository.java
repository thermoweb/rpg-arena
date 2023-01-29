package org.thermoweb.rpg.data.encounters;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.thermoweb.rpg.encounters.EncounterStatus;

import java.util.List;
import java.util.Optional;

public interface EncounterRepository extends MongoRepository<EncounterEntity, String> {

    Optional<EncounterEntity> findFirstByStatus(EncounterStatus status);

    @Query("{ 'characters._id' : ?0 }")
    List<EncounterEntity> findAllByCharacterId(String id);
}
