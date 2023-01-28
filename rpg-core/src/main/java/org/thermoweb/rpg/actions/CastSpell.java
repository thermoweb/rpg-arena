package org.thermoweb.rpg.actions;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.thermoweb.rpg.characters.Ability;
import org.thermoweb.rpg.characters.DefaultCharacter;
import org.thermoweb.rpg.characters.Skills;
import org.thermoweb.rpg.environment.Arena;
import org.thermoweb.rpg.logs.ActionLog;
import org.thermoweb.rpg.logs.SpellLog;
import org.thermoweb.rpg.utils.Damages;
import org.thermoweb.rpg.utils.Dice;
import org.thermoweb.rpg.utils.GridUtils;

import java.util.Objects;

@Builder
@Getter
@Slf4j
public final class CastSpell implements TargetableAction {

    private final Spells spell;
    private DefaultCharacter from;

    private DefaultCharacter target;

    @Override
    public ActionLog execute(Arena arena) throws ActionException {
        autoCheck();
        if (GridUtils.getDirectDistance(arena.getCharacterPairMap().get(from.getId()), arena.getCharacterPairMap().get(target.getId())) > spell.getRange()) {
            throw new ActionException("target is too far to attack.");
        }

        SpellLog.SpellLogBuilder spellLog = SpellLog.builder()
                .from(from.getLog())
                .target(target.getLog())
                .spell(spell);

        log.info("{} casts {} (cost {} hp)", from.getName(), spell.name(), spell.getHpCost());

        int roll = Dice.D100.roll();
        int abilityThreshold = from.getStatistics().getAbility(Ability.INTELLIGENCE);
        String rollLog = String.format("roll %d on %s (%d)", roll, Ability.INTELLIGENCE, abilityThreshold);
        spellLog.roll(rollLog);

        if (roll > abilityThreshold) {
            return spellLog.status(ActionLog.Status.FAILED).outcome("cast failed...").build();
        } else {
            from.spellCostHp(spell.getHpCost());
            Damages.DamagesLog loggedDamages = spell.getLoggedDamages();
            target.takeDamage(loggedDamages.total());

            return spellLog
                    .status(ActionLog.Status.SUCCESS)
                    .damages(loggedDamages)
                    .outcome(String.format("%s taking %d damages (%d raw). %d hit points left",
                            target.getName(),
                            loggedDamages.total(),
                            loggedDamages.total(),
                            target.getHitPoints()))
                    .build();
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

    @Override
    public void setTarget(DefaultCharacter character) {
        this.target = character;
    }
}
