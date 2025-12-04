package me.frenz;

public enum Direction {
    UP(0, -1),
    DOWN(0, 1),
    LEFT(-1, 0),
    RIGHT(1, 0),
    UP_LEFT(-1, -1),
    UP_RIGHT(1, -1),
    DOWN_LEFT(-1, 1),
    DOWN_RIGHT(1, 1);

    private final int x;
    private final int y;

    Direction(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int x() {
        return x;
    }

    public int y() {
        return y;
    }

    public static Direction[] allDirections() {
        return new Direction[] {UP, DOWN, LEFT, RIGHT, UP_LEFT, UP_RIGHT, DOWN_LEFT, DOWN_RIGHT};
    }

    public static Direction[] diagonalDirections() {
        return new Direction[] {UP_LEFT, UP_RIGHT, DOWN_LEFT, DOWN_RIGHT};
    }

    public Direction turnRight() {
        return switch (this) {
            case UP -> RIGHT;
            case DOWN -> LEFT;
            case LEFT -> UP;
            case RIGHT -> DOWN;
            case UP_LEFT -> UP_RIGHT;
            case UP_RIGHT -> DOWN_RIGHT;
            case DOWN_LEFT -> UP_LEFT;
            case DOWN_RIGHT -> DOWN_LEFT;
        };
    }

    public Direction turnLeft() {
        return switch (this) {
            case UP -> LEFT;
            case DOWN -> RIGHT;
            case LEFT -> DOWN;
            case RIGHT -> UP;
            case UP_LEFT -> DOWN_LEFT;
            case UP_RIGHT -> UP_LEFT;
            case DOWN_LEFT -> DOWN_RIGHT;
            case DOWN_RIGHT -> UP_RIGHT;
        };
    }
}
