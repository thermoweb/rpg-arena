package org.thermoweb.rpg.arena;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonKey;
import lombok.Getter;

@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Grid {
    SQUARE_8(8, 8),
    SQUARE_16(16, 16),
    SQUARE_32(32, 32);

    private final int x;
    private final int y;

    Grid(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @JsonKey
    public String getName() {
        return this.name();
    }
}
