package me.frenz.day09;

record Coordinate(int x, int y) {

    Coordinate moveOneStep(final Move move) {
        return new Coordinate(x + move.x(), y + move.y());
    }

    boolean isTouching(final Coordinate other) {
        return Math.abs(x - other.x) <= 1 && Math.abs(y - other.y) <= 1;
    }
}
