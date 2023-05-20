package me.frenz.day12;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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
