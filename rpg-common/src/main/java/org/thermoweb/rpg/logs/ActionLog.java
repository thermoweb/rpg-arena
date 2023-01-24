package org.thermoweb.rpg.logs;

public sealed interface ActionLog permits AttackLog, MoveLog, SpellLog {
}
