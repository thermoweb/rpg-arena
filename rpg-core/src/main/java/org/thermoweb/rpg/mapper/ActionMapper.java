package org.thermoweb.rpg.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.thermoweb.rpg.actions.Action;
import org.thermoweb.rpg.actions.Attack;
import org.thermoweb.rpg.actions.CastSpell;
import org.thermoweb.rpg.actions.Direction;
import org.thermoweb.rpg.actions.Move;
import org.thermoweb.rpg.actions.Spells;
import org.thermoweb.rpg.dto.CharacterDto;
import org.thermoweb.rpg.dto.action.ActionDto;
import org.thermoweb.rpg.dto.action.ActionType;
import org.thermoweb.rpg.equipment.Weapon;

import java.util.Map;

public class ActionMapper {

    private static final String DIRECTION = "direction";
    private static final String WEAPON = "weapon";
    private static final String TARGET = "target";
    private static final String SPELL = "spell";

    public static ActionDto map(Action action) {
        if (action instanceof Move) {
            return new ActionDto(ActionType.MOVE, Map.of(DIRECTION, ((Move) action).getDirection()));
        } else if (action instanceof Attack) {
            return new ActionDto(
                    ActionType.ATTACK,
                    Map.of(WEAPON, ((Attack) action).getWeapon(),
                            TARGET, CharacterDtoMapper.map(((Attack) action).getTarget())));
        } else if (action instanceof CastSpell) {
            return new ActionDto(
                    ActionType.SPELL,
                    Map.of(SPELL, ((CastSpell) action).getSpell(),
                            TARGET, CharacterDtoMapper.map(((CastSpell) action).getTarget()))
            );
        }

        return null;
    }

    public static Action map(ActionDto action) {
        return switch (action.type()) {
            case ATTACK -> Attack.builder()
                    .weapon(Weapon.valueOf((String) action.parameters().get(WEAPON)))
                    .target(CharacterDtoMapper.map((new ObjectMapper()).convertValue(action.parameters().get(TARGET), CharacterDto.class)))
                    .build();
            case MOVE ->
                    Move.builder().direction(Direction.valueOf((String) action.parameters().get(DIRECTION))).build();
            case SPELL -> CastSpell.builder()
                    .spell((Spells) action.parameters().get(SPELL))
                    .target(CharacterDtoMapper.map((new ObjectMapper()).convertValue(action.parameters().get(TARGET), CharacterDto.class)))
                    .build();
        };
    }
}
