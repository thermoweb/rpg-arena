package org.thermoweb.rpg.data.characters;

import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CharacterRepository extends MongoRepository<CharacterEntity, String> {

    @Aggregation({"{$sample:{size:2}}"})
    AggregationResults<CharacterEntity> random();
}
