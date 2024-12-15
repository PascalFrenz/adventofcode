package me.frenz;

public record Position(int x, int y) {

    public Position move(Direction direction) {
        return new Position(x + direction.x(), y + direction.y());
    }
}
