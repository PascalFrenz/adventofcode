package me.frenz.day07;

public enum Card {
    TWO,
    THREE,
    FOUR,
    FIVE,
    SIX,
    SEVEN,
    EIGHT,
    NINE,
    T,
    J,
    Q,
    K,
    A;

    static Card from(final String s) {
        return switch (s) {
            case "2" -> TWO;
            case "3" -> THREE;
            case "4" -> FOUR;
            case "5" -> FIVE;
            case "6" -> SIX;
            case "7" -> SEVEN;
            case "8" -> EIGHT;
            case "9" -> NINE;
            case "T" -> T;
            case "J" -> J;
            case "Q" -> Q;
            case "K" -> K;
            case "A" -> A;
            case null, default -> throw new IllegalArgumentException("Unknown card: " + s);
        };
    }
}
