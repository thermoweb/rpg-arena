package org.thermoweb.rpg.actions;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.thermoweb.rpg.characters.Ability;
import org.thermoweb.rpg.characters.DefaultCharacter;
import org.thermoweb.rpg.characters.Skills;
import org.thermoweb.rpg.environment.Arena;
import org.thermoweb.rpg.utils.GridUtils;

import java.util.Objects;

@Builder
@Getter
@Slf4j
public final class CastSpell implements Action {

    private final DefaultCharacter target;
    private final Spells spell;
    private DefaultCharacter from;

    @Override
    public String execute(Arena arena) throws ActionException {
        autoCheck();
        if (GridUtils.getDirectDistance(arena.getCharacterPairMap().get(from.getId()), arena.getCharacterPairMap().get(target.getId())) > spell.getRange()) {
            throw new ActionException("target is too far to attack.");
        }

        log.info("{} casts {} (cost {} hp)", from.getName(), spell.name(), spell.getHpCost());
        from.spellCostHp(spell.getHpCost());
        if (from.rollAbility(Ability.INTELLIGENCE)) {
            int damages = spell.getDamages();
            return target.takeSpellDamages(damages);
        } else {
            String actionLog = "spell missed...";
            log.info(actionLog);
            return actionLog;
        }
    }

    @Override
    public void setOwner(DefaultCharacter owner) {
        this.from = owner;
    }

    private void autoCheck() throws ActionException {
        try {
            Objects.requireNonNull(spell, "'spell' should be not null");
            Objects.requireNonNull(from, "'from' should be not null");
            Objects.requireNonNull(target, "'target' should be not null");
            if (!from.getSkills().contains(Skills.SPELL_CASTING)) {
                throw new ActionException("character must have the skill to cast spell");
            }
            if (!from.getSpellbook().contains(spell)) {
                throw new ActionException("this spell is not in the character's spellbook");
            }
        } catch (NullPointerException e) {
            throw new ActionException("Action is not valid", e);
        }
    }
}
