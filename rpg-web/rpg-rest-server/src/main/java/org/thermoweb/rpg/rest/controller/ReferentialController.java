package org.thermoweb.rpg.rest.controller;

import lombok.Getter;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.thermoweb.rpg.arena.Grid;
import org.thermoweb.rpg.characters.Ability;
import org.thermoweb.rpg.characters.BrainType;
import org.thermoweb.rpg.characters.Profiles;
import org.thermoweb.rpg.characters.Species;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/referential")
public class ReferentialController {

    @GetMapping
    public Referential getReferential() {
        return new Referential();
    }

    @Getter
    static class Referential {
        private final Map<String, Grid> grid = Arrays.stream(Grid.values()).collect(Collectors.toMap(Grid::name, Function.identity()));
        private final List<BrainType> brainTypes = List.of(BrainType.values());
        private final List<Profiles> profiles = List.of(Profiles.values());
        private final List<Species> species = List.of(Species.values());
        private final List<Ability> abilities = List.of(Ability.values());
    }
}
