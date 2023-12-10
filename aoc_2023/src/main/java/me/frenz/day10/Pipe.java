package me.frenz.day10;

import me.frenz.Pair;

public class Pipe {
    private final String value;

    public Pipe(String value) {
        this.value = value;
    }

    Pair<Integer, Integer> getMovement() {
        return switch (value) {
            case "|" -> Pair.of(0, 1);
            case "-" -> Pair.of(1, 0);
            case "L" -> Pair.of(1, 0);
            case null, default -> throw new IllegalStateException("Unexpected value: " + value);
        };
    }
}
