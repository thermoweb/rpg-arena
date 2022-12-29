package org.thermoweb.rpg.data.characters;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface CharacterRepository  extends MongoRepository<CharacterEntity, String> {
}
