package me.frenz.day11;

enum Seat {
    FLOOR("."),
    EMPTY("L"),
    OCCUPIED("#");

    final String value;

    Seat(final String value) {
        this.value = value;
    }

    static Seat[] from(final String line) {
        final Seat[] seats = new Seat[line.length()];
        char[] charArray = line.toCharArray();

        for (int i = 0; i < charArray.length; i++) {
            seats[i] = Seat.of(charArray[i]);
        }

        return seats;
    }

    static Seat of(char sign) {
        switch (sign) {
            case '.' -> {
                return FLOOR;
            }
            case 'L' -> {
                return EMPTY;
            }
            case '#' -> {
                return OCCUPIED;
            }
            default -> throw new IllegalArgumentException("Character not supported: %s".formatted(sign));
        }
    }


    @Override
    public String toString() {
        return value;
    }
}
