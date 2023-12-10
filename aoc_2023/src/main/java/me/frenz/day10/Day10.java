
package me.frenz.day10;

import me.frenz.Day;

import java.util.*;
import java.util.stream.IntStream;

public class Day10 extends Day<Integer, Integer> {

    private final HashMap<Coord, String> coords = new HashMap<>();
    private final HashMap<String, List<Coord>> pipes = new HashMap<>();
    private final Set<Coord> visited = new HashSet<>();

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
    }

    @Override
    protected Integer part1() {
        final Coord startingPosition = findStartingPosition();

        Stack<Coord> toVisit = new Stack<>();
        toVisit.add(startingPosition);
        int steps = 0;
        do {
            final Coord currentPosition = toVisit.pop();
            visited.add(currentPosition);
            final List<Coord> nextPositions = getNextPositions(currentPosition, visited);
            if (!nextPositions.isEmpty()) {
                toVisit.push(nextPositions.getFirst());
            }
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
                final int lineCrosses = lineCrosses(coord);
                result += lineCrosses % 2 == 1 ? 1 : 0;
            }
        }
        return result;
    }

    int lineCrosses(final Coord coord) {
        final String line = input.get(coord.y()).substring(0, coord.x() + 1);
        final String[] chars = line.split("");
        final List<String> wallParts = IntStream.range(0, chars.length)
                .mapToObj(it -> new Coord(it, coord.y()))
                .map(coords::get)
                .filter(it -> !("-".equals(it) || ".".equals(it)))
                .toList();
        int numOfCrosses = 0;
        boolean top = false;
        boolean bottom = false;
        for (String wallPart : wallParts) {
            boolean isCrossing = wallPart.equals("|");
            if (wallPart.equals("J") || wallPart.equals("L")) {
                if (bottom) {
                    isCrossing = true;
                } else {
                    top = !top;
                }
            }
            if (wallPart.equals("F") || wallPart.equals("7") || wallPart.equals("S")) {
                if (top) {
                    isCrossing = true;
                } else {
                    bottom = !bottom;
                }
            }

            if (isCrossing && !visited.contains(coord)) {
                numOfCrosses++;
            }
        }
        return numOfCrosses;
    }

    private List<Coord> getNextPositions(final Coord currentPosition, final Set<Coord> visited) {
        List<Coord> list = new ArrayList<>();
        for (Coord it : Arrays.asList(
                new Coord(0, -1),
                new Coord(-1, 0),
                new Coord(1, 0),
                new Coord(0, 1)
        )) {
            Coord coord = new Coord(currentPosition.x() + it.x(), currentPosition.y() + it.y());

            if (!"S".equals(coords.get(coord))) {
                if (this.validMove(currentPosition, it)) {
                    if (!visited.contains(coord)) {
                        list.add(coord);
                    }
                }
            }
        }
        return list;
    }

    private Coord findStartingPosition() {
        if (pipes.get("S").size() > 1 || pipes.get("S").isEmpty()) {
            throw new IllegalArgumentException("More than one or no starting point found!");
        }

        return pipes.get("S").getFirst();
    }

    private boolean validMove(Coord from, Coord movement) {
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

}
