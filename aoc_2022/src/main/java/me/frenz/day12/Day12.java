package me.frenz.day12;

import me.frenz.Day;

import java.util.*;
import java.util.stream.Collectors;


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
