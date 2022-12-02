package me.frenz.day02;

enum RPSHand {
    ROCK("AX", 1),
    PAPER("BY", 2),
    SCISSORS("CZ", 3);

    public final String letters;
    public final int value;

    RPSHand(final String letters, final int value) {
        this.letters = letters;
        this.value = value;
    }

    static RPSHand from(final String letter) {
        if (letter.length() != 1) {
            throw new IllegalArgumentException("You shall only pass one letter");
        }

        if (ROCK.letters.contains(letter)) {
            return ROCK;
        } else if (PAPER.letters.contains(letter)) {
            return PAPER;
        } else if (SCISSORS.letters.contains(letter)) {
            return SCISSORS;
        } else {
            throw new IllegalArgumentException("The given letter \"%s\" is unsupported!".formatted(letter));
        }
    }
}
