package me.frenz.day12;

import me.frenz.Day;

import java.util.*;
import java.util.stream.Collectors;

record Position(int x, int y) {
    Position add(final Position other) {
        return new Position(x + other.x, y + other.y);
    }
}


record Heightmap(Position start, Position end, int width, int height, int[] map, Set<Position> lowestPoints) {
    static final Set<Position> ONE_STEP_DIRECTIONS = Set.of(
            new Position(1, 0),
            new Position(-1, 0),
            new Position(0, -1),
            new Position(0, 1)
    );

    static Optional<Heightmap> parseAndConvertHeightmap(final List<String> input) {
        int[] map = null;
        int width = 0, height = input.size();
        final Set<Position> lowestPoints = new HashSet<>();
        Position start = null, end = null;
        for (int y = 0; y < input.size(); y++) {
            final char[] chars = input.get(y).toCharArray();
            width = chars.length;
            if (map == null) {
                map = new int[input.size() * chars.length];
            }
            for (int x = 0; x < chars.length; x++) {
                if (chars[x] == 'S') {
                    map[y * width + x] = 0;
                    start = new Position(x, y);
                } else if (chars[x] == 'E') {
                    map[y * width + x] = Character.getNumericValue('z') - Character.getNumericValue('a');
                    end = new Position(x, y);
                } else if (chars[x] >= 'a' && chars[x] <= 'z') {
                    if (chars[x] == 'a') {
                        lowestPoints.add(new Position(x, y));
                    }
                    map[y * width + x] = Character.getNumericValue(chars[x]) - Character.getNumericValue('a');
                } else {
                    throw new IllegalArgumentException("Character not expected");
                }
            }
        }
        if (map != null && start != null && end != null) {
            return Optional.of(new Heightmap(start, end, width, height, map, lowestPoints));
        } else {
            return Optional.empty();
        }
    }

    int heightAt(final Position pos) {
        return map[pos.y() * width + pos.x()];
    }

    boolean isValid(final Position pos) {
        return pos.x() >= 0 && pos.x() < width() && pos.y() >= 0 && pos.y() < height;
    }

    Set<Position> getWalkableNeighbours(final Position p) {
        return ONE_STEP_DIRECTIONS.stream()
                .map(p::add)
                .filter(this::isValid)
                .filter(it -> this.heightAt(it) <= this.heightAt(p) + 1)
                .collect(Collectors.toSet());
    }
}


public class Day12 extends Day<Integer, Integer> {

    public Day12(final List<String> input) {
        super(input);
    }

    @Override
    protected Integer part1() {
        final Optional<Heightmap> heightmap = Heightmap.parseAndConvertHeightmap(input);
        return heightmap.map(map -> {
            Set<Position> startingPoints = new HashSet<>();
            startingPoints.add(map.start());
            return bfs(map, startingPoints);
        }).orElse(-1);
    }

    @Override
    protected Integer part2() {
        final Optional<Heightmap> heightmap = Heightmap.parseAndConvertHeightmap(input);
        return heightmap.map(map -> {
            Set<Position> startingPoints = new HashSet<>();
            startingPoints.add(map.start());
            startingPoints.addAll(map.lowestPoints());
            return bfs(map, startingPoints);
        }).orElse(-1);
    }

    private static int bfs(Heightmap map, Set<Position> current) {
        final Set<Position> visited = new HashSet<>();
        int step = 0;
        while (current != null) {
            final Set<Position> next = new HashSet<>();
            for (Position curr : current) {
                for (Position neighbour : map.getWalkableNeighbours(curr)) {
                    if (!visited.contains(neighbour)) {
                        visited.add(neighbour);
                        next.add(neighbour);
                    }
                }
            }
            if (current.contains(map.end())) {
                current = null;
            } else {
                current = next;
                step++;
            }
        }
        return step;
    }
}
