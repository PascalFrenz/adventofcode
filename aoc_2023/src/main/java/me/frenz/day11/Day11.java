
package me.frenz.day11;

import me.frenz.Day;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Day11 extends Day<Integer, Integer> {

    public Day11(List<String> input) {
        super(input);
    }

    @Override
    protected Integer part1() {
        final List<String> expandedMap = expandMap();
        final Set<Coordinate> galaxies = findGalaxies(expandedMap);

        return 0;
    }

    private static Set<Coordinate> findGalaxies(List<String> expandedMap) {
        final Set<Coordinate> galaxies = new HashSet<>();
        for (int y = 0; y < expandedMap.size(); y++) {
            final String line = expandedMap.get(y);
            for (int x = 0; x < line.split("").length; x++) {
                if (line.charAt(x) == '#') {
                    galaxies.add(new Coordinate(x, y));
                }
            }
        }
        return galaxies;
    }

    @Override
    protected Integer part2() {
        return 0;
    }

    List<String> expandMap() {
        final List<Integer> expandAt = IntStream.range(0, input.get(0).length()).boxed().collect(Collectors.toList());
        final List<String> rowsExpanded = input.stream()
                .flatMap(line -> {
                    if (!line.contains("#")) {
                        return Stream.of(line, line);
                    } else {
                        for (int x = 0; x < line.split("").length; x++) {
                            if ('#' == line.charAt(x)) {
                                expandAt.remove(Integer.valueOf(x));
                            }
                        }
                        return Stream.of(line);
                    }
                })
                .toList();

        final List<String> expanded = new ArrayList<>(rowsExpanded.size());
        for (String line : rowsExpanded) {
            String newLine = line;
            for (int i = 0; i < expandAt.size(); i++) {
                Integer x = expandAt.get(i);
                newLine = newLine.substring(0, x + i).concat(".").concat(newLine.substring(x + i));
            }
            expanded.add(newLine);
        }
        return expanded;
    }

}
