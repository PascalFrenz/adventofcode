package me.frenz.day12;

import lombok.Getter;

public class Ship {
    @Getter
    private int north;

    @Getter
    private int east;

    private Direction currentlyFacing;

    public Ship(int north, int east) {
        this.north = north;
        this.east = east;
        this.currentlyFacing = Direction.EAST;
    }

    public int manhattanDistance() {
        return Math.abs(this.getEast()) + Math.abs(this.getNorth());
    }

    public void move(String command) {
        final String cmdDir = command.substring(0, 1);
        final int steps = Integer.parseInt(command.substring(1));
        switch (cmdDir) {
            case "N" -> this.north += steps;
            case "S" -> this.north -= steps;
            case "E" -> this.east += steps;
            case "W" -> this.east -= steps;
            case "L" -> this.currentlyFacing = turnLeft(this.currentlyFacing, steps);
            case "R" -> this.currentlyFacing = turnRight(this.currentlyFacing, steps);
            case "F" -> {
                switch (this.currentlyFacing) {
                    case NORTH -> this.north += steps;
                    case SOUTH -> this.north -= steps;
                    case EAST -> this.east += steps;
                    case WEST -> this.east -= steps;
                }
            }
            default -> throw new IllegalArgumentException("Unknown command! %s".formatted(command));
        }
    }

    private Direction turnRight(final Direction currentDirection, int degrees) {
        if (degrees == 0) {
            return currentDirection;
        } else {
            Direction nextDirection = switch (currentDirection) {
                case NORTH -> Direction.EAST;
                case SOUTH -> Direction.WEST;
                case EAST -> Direction.SOUTH;
                case WEST -> Direction.NORTH;
            };
            return turnRight(nextDirection, degrees - 90);
        }
    }

    private Direction turnLeft(Direction currentDirection, int degrees) {
        if (degrees == 0) {
            return currentDirection;
        } else {
            Direction nextDirection = switch (currentDirection) {
                case NORTH -> Direction.WEST;
                case SOUTH -> Direction.EAST;
                case EAST -> Direction.NORTH;
                case WEST -> Direction.SOUTH;
            };
            return turnLeft(nextDirection, degrees - 90);
        }
    }

    enum Direction {
        NORTH("N"),
        SOUTH("S"),
        EAST("E"),
        WEST("W");

        final String value;

        Direction(final String value) {
            this.value = value;
        }
    }
}
