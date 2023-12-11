
package me.frenz.day11;

import me.frenz.Day;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Day11 extends Day<Integer, Long> {

    private final int expandFactor;

    public Day11(List<String> input) {
        super(input);
        expandFactor = -1;
    }

    Day11(List<String> input, int expandFactor) {
        super(input);
        this.expandFactor = expandFactor;
    }

    @Override
    protected Integer part1() {
        final List<String> expandedMap = expandMap();
        final Set<Coordinate> galaxies = findGalaxies(expandedMap, expandFactor == -1 ? 2 : expandFactor);
        return (int) calculateSumOfDistances(galaxies);
    }

    @Override
    protected Long part2() {
        final List<String> expandedMap = expandMap();
        final Set<Coordinate> galaxies = findGalaxies(expandedMap, expandFactor == -1 ? 1000000 : expandFactor);
        return calculateSumOfDistances(galaxies);
    }

    private static long calculateSumOfDistances(Set<Coordinate> galaxies) {
        long distance = 0;
        for (Coordinate galaxy : galaxies) {
            for (Coordinate otherGalaxy : galaxies) {
                distance += Math.abs(galaxy.x() - otherGalaxy.x()) + Math.abs(galaxy.y() - otherGalaxy.y());
            }
        }
        return distance / 2;
    }

    private static Set<Coordinate> findGalaxies(List<String> expandedMap, int expandFactor) {
        final Set<Coordinate> galaxies = new HashSet<>();
        int yExpands = 0;
        for (int y = 0; y < expandedMap.size(); y++) {
            final String line = expandedMap.get(y);
            yExpands += line.contains("-") ? 1 : 0;
            for (int x = 0; x < line.split("").length; x++) {
                if (line.charAt(x) == '#') {
                    final String[] split = line.substring(0, x).split("");
                    final int xExpands = Math.toIntExact(Arrays.stream(split).filter("|"::equals).count());

                    final int xCoord = x + xExpands * expandFactor - xExpands;
                    final int yCoord = y + yExpands * expandFactor - yExpands;
                    galaxies.add(new Coordinate(xCoord, yCoord));
                }
            }
        }
        return galaxies;
    }

    List<String> expandMap() {
        final List<Integer> expandAt = IntStream.range(0, input.get(0).length()).boxed().collect(Collectors.toList());
        final List<String> rowsExpanded = input.stream()
                .flatMap(line -> {
                    if (!line.contains("#")) {
                        return Stream.of(line.replace(".", "-"));
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
            for (Integer x : expandAt) {
                newLine = newLine.substring(0, x).concat("|").concat(newLine.substring(x + 1));
            }
            expanded.add(newLine);
        }
        return expanded;
    }

}
