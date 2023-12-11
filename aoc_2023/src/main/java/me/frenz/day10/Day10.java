
package me.frenz.day10;

import me.frenz.Day;

import java.util.*;
import java.util.stream.IntStream;

public class Day10 extends Day<Integer, Integer> {

    private final HashMap<Coord, String> coords = new HashMap<>();
    private final HashMap<String, List<Coord>> pipes = new HashMap<>();
    private final Map<Coord, Integer> visited = new HashMap<>();
    private final Coord startingPosition;

    public Day10(List<String> input) {
        super(input);

        for (int row = 0; row < input.size(); row++) {
            final String line = input.get(row);
            final String[] chars = line.split("");
            for (int col = 0; col < chars.length; col++) {
                coords.put(new Coord(col, row), chars[col]);

                pipes.putIfAbsent(chars[col], new ArrayList<>());
                pipes.get(chars[col]).add(new Coord(col, row));
            }
        }

        startingPosition = findStartingPosition();
        coords.put(startingPosition, findStartSymbol());
    }

    @Override
    protected Integer part1() {
        visited.clear();
        Stack<Coord> toVisit = new Stack<>();
        toVisit.add(startingPosition);
        do {
            final Coord currentPosition = toVisit.pop();
            visited.put(currentPosition, visited.size());
            final List<Coord> nextPositions = getNextPositions(currentPosition, visited, coords);
            toVisit.addAll(nextPositions);
        } while (!toVisit.isEmpty());

        return visited.size() / 2;
    }

    @Override
    protected Integer part2() {
        part1();
        int result = 0;
        for (int line = 0; line < input.size(); line++) {
            for (int column = 0; column < input.get(line).split("").length; column++) {
                final Coord coord = new Coord(column, line);
                if (!visited.containsKey(coord)) {
                    final int lineCrosses = lineCrosses(coord);
                    result += lineCrosses % 2 == 1 ? 1 : 0;
                }
            }
        }
        return result;
    }

    int lineCrosses(final Coord coord) {
        final String line = input.get(coord.y()).substring(0, coord.x() + 1);
        final String[] chars = line.split("");
        final List<String> wallParts = IntStream.range(0, chars.length)
                .mapToObj(it -> new Coord(it, coord.y()))
                .filter(visited::containsKey)
                .map(coords::get)
                .filter(it -> !("-".equals(it) || ".".equals(it) || "J".equals(it) || "L".equals(it)))
                .toList();
        return wallParts.size();
    }

    private List<Coord> getNextPositions(final Coord currentPosition, final Map<Coord, Integer> visited, HashMap<Coord, String> coords) {
        List<Coord> list = new ArrayList<>();
        for (Direction direction : Direction.values()) {
            final Coord coord = direction.move(currentPosition);

            final boolean isValidMoveTowards = this.validMove(currentPosition, direction, coords);
            final boolean isValidMoveFrom = this.validMove(direction.move(currentPosition), direction.opposite(), coords);

            if (!visited.containsKey(coord) && isValidMoveTowards && isValidMoveFrom) {
                list.add(coord);
            }
        }
        return list;
    }

    String findStartSymbol() {
        final Coord startingPosition = findStartingPosition();
        final List<String> symbols = List.of(
                "|",
                "-",
                "L",
                "J",
                "F",
                "7"
        );
        for (String symbol : symbols) {
            final List<String> startReplaced = input.stream().map(it -> it.replace("S", symbol)).toList();
            final HashMap<Coord, String> newCoords = new HashMap<>();
            for (int row = 0; row < startReplaced.size(); row++) {
                final String line = startReplaced.get(row);
                final String[] chars = line.split("");
                for (int col = 0; col < chars.length; col++) {
                    newCoords.put(new Coord(col, row), chars[col]);
                }
            }

            final List<Coord> nextPositions = getNextPositions(startingPosition, new HashMap<>(), newCoords);
            if (nextPositions.size() == 2) {
                return symbol;
            }
        }
        throw new IllegalArgumentException("No valid start symbol found");
    }

    private Coord findStartingPosition() {
        if (pipes.get("S").size() > 1 || pipes.get("S").isEmpty()) {
            throw new IllegalArgumentException("More than one or no starting point found!");
        }

        return pipes.get("S").getFirst();
    }

    private boolean validMove(final Coord from, final Direction direction, final HashMap<Coord, String> coords) {
        final Coord newPos = direction.move(from);
        if (!coords.containsKey(newPos) || ".".equals(coords.get(newPos))) {
            return false;
        }

        final String pipe = coords.get(from);
        return switch (pipe) {
            case "|" -> (direction == Direction.NORTH || direction == Direction.SOUTH);
            case "-" -> (direction == Direction.EAST || direction == Direction.WEST);
            case "L" -> (direction == Direction.NORTH || direction == Direction.EAST);
            case "J" -> (direction == Direction.NORTH || direction == Direction.WEST);
            case "7" -> (direction == Direction.SOUTH || direction == Direction.WEST);
            case "F" -> (direction == Direction.SOUTH || direction == Direction.EAST);
            case "S" -> true;
            case null, default -> false;
        };
    }

    record Coord(int x, int y) { }

    enum Direction {
        NORTH(new Coord(0, -1)),
        SOUTH(new Coord(0, 1)),
        EAST(new Coord(1, 0)),
        WEST(new Coord(-1, 0));

        private final Coord move;

        Direction(final Coord move) {
            this.move = move;
        }

        Coord move(final Coord pos) {
            return new Coord(pos.x + this.move.x, pos.y + this.move.y);
        }

        Direction opposite() {
            return switch (this) {
                case NORTH -> SOUTH;
                case SOUTH -> NORTH;
                case EAST -> WEST;
                case WEST -> EAST;
            };
        }
    }

}
