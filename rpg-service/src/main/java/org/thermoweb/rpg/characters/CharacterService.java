package org.thermoweb.rpg.characters;

import org.springframework.stereotype.Service;
import org.thermoweb.rpg.data.characters.CharacterEntity;
import org.thermoweb.rpg.data.characters.CharacterRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CharacterService {

    private final CharacterRepository characterRepository;

    public CharacterService(CharacterRepository characterRepository) {
        this.characterRepository = characterRepository;
    }

    public List<CharacterEntity> getAll() {
        return characterRepository.findAll();
    }

    public CharacterEntity create(CharacterEntity character) {
        return characterRepository.save(character);
    }

    public void deleteAll() {
        characterRepository.deleteAll();
    }

    public Optional<CharacterEntity> getById(String id) {
        return characterRepository.findById(id);
    }
}
