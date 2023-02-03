package org.thermoweb.rpg.encounter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.thermoweb.rpg.data.encounters.EncounterRepository;
import org.thermoweb.rpg.encounter.states.CreatedState;
import org.thermoweb.rpg.encounter.states.FailedState;
import org.thermoweb.rpg.encounters.EncounterStatus;

import java.time.InstantSource;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class EncounterService {


    private final EncounterRepository encounterRepository;

    public EncounterService(EncounterRepository encounterRepository) {
        this.encounterRepository = encounterRepository;
    }

    public List<Encounter> findAll() {
        return encounterRepository.findAll().stream().map(EncounterEntityMapper::map).collect(Collectors.toList());
    }

    public Page<Encounter> findAll(Pageable pageable) {
        return encounterRepository.findAll(pageable).map(EncounterEntityMapper::map);
    }

    public List<Encounter> findAllByCharacterId(String id) {
        return encounterRepository.findAllByCharacterId(id).stream().map(EncounterEntityMapper::map).collect(Collectors.toList());
    }

    public Encounter create(Encounter encounter) {
        encounter.setState(new CreatedState());
        encounter.setLastModified(InstantSource.system().instant());
        return EncounterEntityMapper.map(encounterRepository.save(EncounterEntityMapper.map(encounter)));
    }

    public Optional<Encounter> findById(String id) {
        return encounterRepository.findById(id).map(EncounterEntityMapper::map);
    }

    public Optional<Encounter> findOneRunnableEncounter() {
        return encounterRepository.findFirstByStatus(EncounterStatus.QUEUED).map(EncounterEntityMapper::map);
    }

    public Encounter launch(Encounter encounter) {
        try {
            encounter.prepare();
            return EncounterEntityMapper.map(encounterRepository.save(EncounterEntityMapper.map(encounter)));
        } catch (Exception e) {
            handleFailedEncounter("Launch failed", e, encounter);
        }
        return encounter;
    }

    public void run(Encounter encounter) {
        try {
            encounter.run();
            save(encounter);
            encounter.run();
            save(encounter);
        } catch (Exception e) {
            handleFailedEncounter("Run failed", e, encounter);
        }
    }

    public void save(Encounter encounter) {
        encounter.setLastModified(InstantSource.system().instant());
        encounterRepository.save(EncounterEntityMapper.map(encounter));
    }

    private void handleFailedEncounter(String message, Exception e, Encounter encounter) {
        log.error(message + " : {}", e.getMessage());
        encounter.setState(new FailedState());
        encounter.getCharacters().forEach(character -> character.setHitPoints(character.getMaxHitPoints()));
        save(encounter);
    }
}
