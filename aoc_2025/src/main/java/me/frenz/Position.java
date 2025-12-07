package me.frenz;

import java.util.Comparator;

public record Position(int x, int y) implements Comparable<Position> {

    public Position move(Direction direction) {
        return new Position(x + direction.x(), y + direction.y());
    }

    @Override
    public int compareTo(Position o) {
        return Comparator.comparing(Position::y).thenComparing(Position::x).compare(this, o);
    }
}
