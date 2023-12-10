
package me.frenz.day10;

import me.frenz.Day;

import java.util.*;
import java.util.stream.IntStream;

public class Day10 extends Day<Integer, Integer> {

    private final HashMap<Coord, String> coords = new HashMap<>();
    private final HashMap<String, List<Coord>> pipes = new HashMap<>();
    private final Set<Coord> visited = new HashSet<>();
    private final String startSymbol;
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

        startSymbol = findStartSymbol();
        startingPosition = findStartingPosition();
        coords.put(startingPosition, startSymbol);
    }

    @Override
    protected Integer part1() {
        Stack<Coord> toVisit = new Stack<>();
        toVisit.add(startingPosition);
        int steps = 0;
        do {
            final Coord currentPosition = toVisit.pop();
            visited.add(currentPosition);
            final List<Coord> nextPositions = getNextPositions(currentPosition, visited, coords);
            toVisit.addAll(nextPositions);
            steps++;
        } while (!toVisit.isEmpty() && steps < 1000000);

        return Math.floorDiv(steps, 2);
    }

    @Override
    protected Integer part2() {
        part1();

        int result = 0;
        for (int line = 0; line < input.size(); line++) {
            for (int column = 0; column < input.get(line).split("").length; column++) {
                final Coord coord = new Coord(column, line);
                if (!visited.contains(coord)) {
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
                .filter(visited::contains)
                .map(coords::get)
                .filter(it -> !("-".equals(it) || ".".equals(it) || "J".equals(it) || "L".equals(it)))
                .toList();
        return wallParts.size();
    }

    private List<Coord> getNextPositions(final Coord currentPosition, final Set<Coord> visited, HashMap<Coord, String> coords) {
        List<Coord> list = new ArrayList<>();
        final List<Coord> neighbours = Arrays.asList(
                new Coord(0, -1),
                new Coord(-1, 0),
                new Coord(1, 0),
                new Coord(0, 1)
        );
        for (Coord it : neighbours) {
            Coord coord = new Coord(currentPosition.x() + it.x(), currentPosition.y() + it.y());

            if (!"S".equals(coords.get(coord)) && this.validMove(currentPosition, it, coords) && !visited.contains(coord)) {
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

            final List<Coord> nextPositions = getNextPositions(startingPosition, new HashSet<>(), newCoords);
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

    private boolean validMove(Coord from, Coord movement, HashMap<Coord, String> coords) {
        final Coord newPos = new Coord(from.x() + movement.x(), from.y() + movement.y());
        if (!coords.containsKey(newPos) || ".".equals(coords.get(newPos))) {
            return false;
        }

        if (movement.x() > 0 && movement.y() > 0) {
            throw new IllegalArgumentException("Cannot move diagonally in one step");
        }

        final String pipe = coords.get(from);
        return switch (pipe) {
            case "|" -> Math.abs(movement.y()) == 1;
            case "-" -> Math.abs(movement.x()) == 1;
            case "L" -> movement.y() == -1 || movement.x() == 1;
            case "J" -> movement.y() == -1 || movement.x() == -1;
            case "7" -> movement.y() == 1 || movement.x() == -1;
            case "F" -> movement.y() == 1 || movement.x() == 1;
            case "S" -> true;
            case null, default -> false;
        };
    }

    static record Coord(int x, int y) {
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Coord coord)) return false;

            if (x != coord.x) return false;
            return y == coord.y;
        }

        @Override
        public int hashCode() {
            int result = x;
            result = 31 * result + y;
            return result;
        }
    }
}
