package org.thermoweb.rpg.encounter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.thermoweb.rpg.data.encounters.EncounterRepository;
import org.thermoweb.rpg.encounter.states.CreatedState;
import org.thermoweb.rpg.encounter.states.EncounterStateException;
import org.thermoweb.rpg.encounter.states.FailedState;
import org.thermoweb.rpg.encounters.EncounterStatus;

import java.util.Optional;

@Service
@Slf4j
public class EncounterService {


    private final EncounterRepository encounterRepository;

    public EncounterService(EncounterRepository encounterRepository) {
        this.encounterRepository = encounterRepository;
    }

    public Encounter create(Encounter encounter) {
        encounter.setState(new CreatedState());
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
        } catch (EncounterStateException e) {
            handleFailedEncounter("Launch failed", e, encounter);
        }
        return encounter;
    }

    public void run(Encounter encounter) {
        try {
            encounter.run();
            encounterRepository.save(EncounterEntityMapper.map(encounter));
            encounter.run();
            encounterRepository.save(EncounterEntityMapper.map(encounter));
        } catch (EncounterStateException e) {
            handleFailedEncounter("Run failed", e, encounter);
        }
    }

    private void handleFailedEncounter(String message, EncounterStateException e, Encounter encounter) {
        log.error(message + " : {}", e.getMessage());
        encounter.setState(new FailedState());
        encounterRepository.save(EncounterEntityMapper.map(encounter));
    }
}
