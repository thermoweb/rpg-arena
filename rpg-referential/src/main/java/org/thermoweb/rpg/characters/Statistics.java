package org.thermoweb.rpg.characters;

import lombok.Builder;

@Builder(toBuilder = true)
public record Statistics(int force, int dexterity, int intelligence) {

    public int getAbility(Ability ability) {
        return switch (ability) {
            case FORCE -> force;
            case DEXTERITY -> dexterity;
            case INTELLIGENCE -> intelligence;
        };
    }

    public Statistics merge(Statistics statistics) {
        return new Statistics(this.force + statistics.force,
                this.dexterity + statistics.dexterity,
                this.intelligence + statistics.intelligence);
    }

    public record StartStatistics(Ability weakness, Ability medium, Ability strength) {
        public Statistics getStatistics() {
            Statistics stats = new Statistics(0, 0, 0);
            stats = getTempStats(weakness, 30, stats);
            stats = getTempStats(medium, 40, stats);
            stats = getTempStats(strength, 50, stats);
            return stats;
        }

        private static Statistics getTempStats(Ability ability, int value, Statistics statistics) {
            return switch (ability) {
                case FORCE -> statistics.toBuilder().force(value).build();
                case DEXTERITY -> statistics.toBuilder().dexterity(value).build();
                case INTELLIGENCE -> statistics.toBuilder().intelligence(value).build();
            };
        }
    }
}
