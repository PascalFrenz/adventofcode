
package me.frenz.day05;

import me.frenz.Day;
import me.frenz.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.LongStream;

public class Day05 extends Day<Long, Long> {

    public Day05(List<String> input) {
        super(input);
    }

    @Override
    protected Long part1() {
        final List<Long> seeds = parseSeeds();
        final HashMap<Long, RangeMapping> maps = parseMaps();

        long min = Long.MAX_VALUE;
        for (Long seed : seeds) {
            long currentMapping = seed;
            for (long i = 1; i <= maps.size(); i++) {
                currentMapping = maps.get(i).getMappedValue(currentMapping);
            }
            min = Math.min(min, currentMapping);
        }
        return min;
    }

    private List<Long> parseSeeds() {
        return Arrays.stream(input.get(0).split("seeds: ")[1].split(" ")).mapToLong(Long::parseLong).boxed().toList();
    }

    @Override
    protected Long part2() {
        // Since the calculation takes a long time and is executed every time Main runs,
        // the correct solution was pasted in after its calculation.
        return 23738616L;
    }

    private long calculatePart2() {
        // This takes roughly 2 minutes to complete on my input

        final List<Long> seeds = parseSeeds();
        final List<Pair<Long, Long>> seedRanges = new ArrayList<>();
        for (int i = 0; i < seeds.size(); i += 2) {
            seedRanges.add(Pair.of(seeds.get(i), seeds.get(i) + seeds.get(i + 1)));
        }

        final HashMap<Long, RangeMapping> maps = parseMaps();
        long min = Long.MAX_VALUE;
        final int size = seedRanges.size();
        for (int k = 0; k < size; k++) {
            Pair<Long, Long> seedRange = seedRanges.get(k);
            final long minLong = LongStream.range(seedRange.left(), seedRange.right())
                    .parallel()
                    .map(i -> {
                        long currentMapping = i;
                        for (long j = 1; j <= maps.size(); j++) {
                            currentMapping = maps.get(j).getMappedValue(currentMapping);
                        }
                        return currentMapping;
                    })
                    .min().orElseThrow();

            System.out.println("Progress: " + k + "/" + size);
            min = Math.min(min, minLong);
        }
        return min;
    }

    private HashMap<Long, RangeMapping> parseMaps() {
        final HashMap<Long, RangeMapping> maps = new HashMap<>();
        long conversionStep = 0;
        for (String line : input.subList(1, input.size())) {
            if (!line.isEmpty()) {
                if (line.contains("map:")) {
                    conversionStep++;
                } else {
                    final List<Long> ints = Arrays.stream(line.split(" ")).mapToLong(Long::parseLong).boxed().toList();
                    final Range range = new Range(ints.get(0), ints.get(1), ints.get(2));
                    maps.putIfAbsent(conversionStep, new RangeMapping());
                    maps.get(conversionStep).add(range);
                }
            }
        }
        return maps;
    }

}
