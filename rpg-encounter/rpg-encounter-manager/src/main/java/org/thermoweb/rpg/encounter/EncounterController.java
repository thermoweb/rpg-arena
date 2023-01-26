package org.thermoweb.rpg.encounter;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.thermoweb.rpg.dto.EncounterDto;
import org.thermoweb.rpg.encounter.client.EncounterManagerCreationRequest;
import org.thermoweb.rpg.encounters.EncounterStatus;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/encounters")
public class EncounterController {

    private final EncounterService encounterService;

    public EncounterController(EncounterService encounterService) {
        this.encounterService = encounterService;
    }

    @GetMapping
    public List<EncounterDto> getAll() {
        return encounterService.findAll().stream().map(EncounterDtoMapper::map).collect(Collectors.toList());
    }

    @GetMapping("{id}")
    public EncounterDto getById(@PathVariable String id) {
        return encounterService.findById(id).map(EncounterDtoMapper::map).orElseThrow();
    }

    @PostMapping("{id}:launch")
    public EncounterDto launchEncounter(@PathVariable String id) {
        Encounter encounter = encounterService.findById(id).orElseThrow();
        return EncounterDtoMapper.map(encounterService.launch(encounter));
    }

    @PostMapping
    public EncounterDto create(@RequestBody EncounterManagerCreationRequest request) {
        return EncounterDtoMapper.map(encounterService.create(EncounterDtoMapper.map(
                EncounterDto.builder()
                        .characters(request.participants())
                        .status(EncounterStatus.CREATED)
                        .grid(request.grid())
                        .build())));
    }
}
