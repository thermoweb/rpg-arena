package org.thermoweb.rpg.front.views;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.thermoweb.rpg.arena.Grid;
import org.thermoweb.rpg.characters.BrainType;
import org.thermoweb.rpg.dto.EncounterDto;
import org.thermoweb.rpg.dto.brain.BrainDto;
import org.thermoweb.rpg.encounter.client.EncounterManagerClient;
import org.thermoweb.rpg.encounter.client.EncounterManagerRestClient;
import org.thermoweb.rpg.front.conf.RpgArenaFrontProperties;
import org.thermoweb.rpg.rest.client.EncounterCreationRequest;
import org.thermoweb.rpg.rest.client.RpgArenaClient;
import org.thermoweb.rpg.rest.client.RpgArenaRestClient;

import java.io.IOException;

@Controller
@RequestMapping("/ui/encounters")
@Slf4j
public class EncountersView {

    private final EncounterManagerClient encounterManagerRestClient;
    private final RpgArenaClient rpgArenaClient;

    public EncountersView(RpgArenaFrontProperties rpgArenaFrontProperties) {
        rpgArenaClient = new RpgArenaRestClient(rpgArenaFrontProperties.getWebServer());
        encounterManagerRestClient = new EncounterManagerRestClient(rpgArenaFrontProperties.getEncounterManager());
    }

    @GetMapping
    public String getEncounters(Model model,
                                @RequestParam(defaultValue = "1") int page,
                                @RequestParam(defaultValue = "10") int size) {
        try {
            Page<EncounterDto> encountersPage = encounterManagerRestClient.getAll(Math.max(0, page - 1), size);
            model.addAttribute("encountersPage", encountersPage);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        return "encounters/list";
    }

    @GetMapping("create")
    public String createEncounter(Model model) {
        model.addAttribute("createEncounterRequest", CreateEncounterForm.builder()
                .brainUri("http://localhost:8083/brain")
                .brainType(BrainType.REMOTE)
                .opponentBrainUri("http://localhost:8083/brain")
                .opponentBrainType(BrainType.REMOTE)
                .build());
        try {
            model.addAttribute("challengers", rpgArenaClient.getAll());
            model.addAttribute("brainTypes", BrainType.values());
            model.addAttribute("grids", Grid.values());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        return "encounters/create";
    }

    @PostMapping("create")
    public String createEncounter(@ModelAttribute CreateEncounterForm request) {
        try {
            EncounterDto encounter = rpgArenaClient.createRandomEncounter(EncounterCreationRequest.builder()
                    .characterId(request.characterId())
                    .opponentId(request.opponentId())
                    .brain(new BrainDto(request.brainType(), request.brainUri()))
                    .opponentBrain(new BrainDto(request.opponentBrainType(), request.opponentBrainUri()))
                    .grid(request.grid())
                    .build());

            return "redirect:/ui/encounters/" + encounter.id();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("{id}")
    public String getEncounter(@PathVariable String id, Model model) {
        try {
            EncounterDto encounter = encounterManagerRestClient.getById(id);
            model.addAttribute("encounter", encounter);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        return "encounters/view";
    }

    @GetMapping("{id}:launch")
    public String launchEncounter(@PathVariable String id, Model model) {
        try {
            EncounterDto encounter = encounterManagerRestClient.launch(id);
            model.addAttribute("encounter", encounter);
            return "redirect:/ui/encounters/" + id;
        } catch (IOException | InterruptedException e) {
            log.error("Error during encounter launch : ", e);
        }
        try {
            EncounterDto encounter = encounterManagerRestClient.getById(id);
            model.addAttribute("encounter", encounter);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        return "encounters/view";
    }
}
