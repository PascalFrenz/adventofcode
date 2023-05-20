package me.frenz.day12;

record Position(int x, int y) {
    Position add(final Position other) {
        return new Position(x + other.x, y + other.y);
    }
}
