package me.frenz.day01;

import lombok.Getter;

@Getter
enum KnobDirection {
    LEFT(-1),
    RIGHT(1);

    private final int value;

    KnobDirection(int i) {
        this.value = i;
    }

    static KnobDirection valueOf(char c) {
        return switch (c) {
            case 'L' -> KnobDirection.LEFT;
            case 'R' -> KnobDirection.RIGHT;
            default -> throw new IllegalArgumentException("Unknown direction: " + c);
        };
    }
}
