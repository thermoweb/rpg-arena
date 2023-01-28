package org.thermoweb.rpg.front.views;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.thermoweb.rpg.dto.EncounterDto;
import org.thermoweb.rpg.encounter.client.EncounterManagerClient;
import org.thermoweb.rpg.encounter.client.EncounterManagerRestClient;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/ui/encounters")
@Slf4j
public class EncountersView {

    private final EncounterManagerClient encounterManagerRestClient = new EncounterManagerRestClient("http", "localhost", "8082");
    ;

    @GetMapping
    public String getEncounters(Model model) {
        try {
            List<EncounterDto> encounters = encounterManagerRestClient.getAll();
            model.addAttribute("encounters", encounters);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        return "encounters_list";
    }

    @GetMapping("{id}")
    public String getEncounter(@PathVariable String id, Model model) {
        try {
            EncounterDto encounter = encounterManagerRestClient.getById(id);
            model.addAttribute("encounter", encounter);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        return "encounter_view";
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

        return "encounter_view";
    }
}
