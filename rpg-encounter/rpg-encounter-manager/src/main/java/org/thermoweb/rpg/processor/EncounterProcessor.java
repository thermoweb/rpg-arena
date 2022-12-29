package org.thermoweb.rpg.processor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.thermoweb.rpg.encounter.EncounterService;

@Service
@Slf4j
public class EncounterProcessor {

    private final EncounterService encounterService;

    public EncounterProcessor(EncounterService encounterService) {
        this.encounterService = encounterService;
    }

    @Scheduled(fixedDelay = 2000)
    public void runEncounters() {
        encounterService.findOneRunnableEncounter()
                .ifPresentOrElse(encounterService::run, () -> log.debug("no encounter to run."));
    }
}
