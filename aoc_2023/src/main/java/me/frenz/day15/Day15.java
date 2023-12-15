
package me.frenz.day15;

import me.frenz.Day;
import me.frenz.Pair;

import java.util.*;

public class Day15 extends Day<Integer, Integer> {

    public Day15(List<String> input) {
        super(input);
    }

    @Override
    protected Integer part1() {
        final String[] initSequence = parseInitSequence();
        return Arrays.stream(initSequence).map(Day15::calculateHash).mapToInt(Integer::valueOf).sum();
    }

    @Override
    protected Integer part2() {
        final String[] initSequence = parseInitSequence();
        final Map<Integer, List<Pair<String, Integer>>> lensArrangement = new HashMap<>();

        parseLensBuildInstructions(initSequence, lensArrangement);
        return computeTotalFocusingPower(lensArrangement);
    }

    private String[] parseInitSequence() {
        return input.getFirst().replaceAll("\\s+", "").split(",");
    }

    private static void parseLensBuildInstructions(final String[] initSequence, final Map<Integer, List<Pair<String, Integer>>> map) {
        for (String line : initSequence) {
            if (line.indexOf("=") > 0) {
                final String[] split = line.split("=");
                final String name = split[0];
                final int value = Integer.parseInt(split[1]);
                final int hash = calculateHash(name);
                map.putIfAbsent(hash, new ArrayList<>(1));
                if (map.get(hash).stream().anyMatch(it -> it.left().equals(name))) {
                    map.get(hash).replaceAll(it -> it.left().equals(name) ? Pair.of(name, value) : it);
                } else {
                    map.get(hash).addLast(Pair.of(name, value));
                }
            } else if (line.indexOf("-") > 0) {
                final String name = line.split("-")[0];
                final int hash = calculateHash(name);
                if (map.containsKey(hash)) {
                    map.get(hash).removeIf(it -> it.left().equals(name));
                }
            } else {
                throw new IllegalArgumentException("Forbidden line: " + line);
            }
        }
    }

    private static int computeTotalFocusingPower(Map<Integer, List<Pair<String, Integer>>> map) {
        int totalFocusingPower = 0;
        for (Map.Entry<Integer, List<Pair<String, Integer>>> lensMap : map.entrySet()) {
            final List<Pair<String, Integer>> lenses = lensMap.getValue();
            final int boxNumber = lensMap.getKey();
            for (int i = 0; i < lenses.size(); i++) {
                int focalLength = lenses.get(i).right();
                totalFocusingPower += (boxNumber + 1) * (i + 1) * focalLength;
            }
        }
        return totalFocusingPower;
    }

    private static int calculateHash(String s) {
        int hash = 0;
        for (char c : s.toCharArray()) {
            hash += c;
            hash *= 17;
            hash %= 256;
        }
        return hash;
    }

}
