package org.thermoweb.rpg.rest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.thermoweb.rpg.actions.Action;
import org.thermoweb.rpg.brain.DumbBrain;
import org.thermoweb.rpg.brain.RemoteBrain;
import org.thermoweb.rpg.dto.brain.BrainActionListRequest;
import org.thermoweb.rpg.dto.brain.BrainActionListResponse;
import org.thermoweb.rpg.mapper.ActionMapper;
import org.thermoweb.rpg.mapper.ArenaDtoMapper;
import org.thermoweb.rpg.mapper.CharacterDtoMapper;

import java.util.List;

@RestController
@RequestMapping("/brain")
@Slf4j
public class BrainController {

    @PostMapping(value = RemoteBrain.ACTION_URI, consumes = {"application/json"})
    public BrainActionListResponse getActions(@RequestBody BrainActionListRequest request) {
        log.debug("getting actions");
        DumbBrain brain = new DumbBrain();
        List<Action> actions = brain.getActions(
                CharacterDtoMapper.map(request.character()),
                ArenaDtoMapper.map(request.arenaDto()));
        return BrainActionListResponse.builder().action(actions.stream().map(ActionMapper::map).toList()).build();
    }
}
