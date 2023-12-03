
package me.frenz.day03;

import me.frenz.Day;
import me.frenz.Pair;

import java.util.*;
import java.util.function.BiConsumer;

public class Day03 extends Day<Integer, Long> {

    private static final String PADDING_SYMBOL = ".";
    private final List<String> schematic;

    public Day03(List<String> input) {
        super(input);
        final int width = this.input.get(0).length();

        // Padding the schematic with dots here makes life simpler when handling
        // adjacency on the edges of the schematic. Since the schematic is +1 in size
        // everywhere now, we can safely compute adjacent positions without worrying.
        // The disadvantage is, that we have to limit our iteration to within the bounds of the
        // original grid. However, this is way simpler than checking bounds everywhere.
        this.schematic = new ArrayList<>(this.input.size() + 2);
        for (String s : this.input) {
            schematic.add(PADDING_SYMBOL + s + PADDING_SYMBOL);
        }
        schematic.add(0, PADDING_SYMBOL.repeat(width + 2));
        schematic.add(PADDING_SYMBOL.repeat(width + 2));
    }

    @Override
    protected Integer part1() {
        final List<Integer> partNumbers = new ArrayList<>(1);

        forEachNumberOnSchematic((num, adjacentPositions) -> {
            final boolean foundSymbol = adjacentPositions.stream()
                    .map(pos -> schematic.get(pos.left()).charAt(pos.right()))
                    .anyMatch(it -> !Character.isDigit(it) && it != '.');

            if (foundSymbol) {
                partNumbers.add(num);
            }
        });

        return partNumbers.stream().reduce(0, Integer::sum);
    }

    @Override
    protected Long part2() {
        final Map<Pair<Integer, Integer>, List<Integer>> gears = extractGearPositions();

        forEachNumberOnSchematic((num, adjacentPositions) -> {
            for (Pair<Integer, Integer> pos : adjacentPositions) {
                if (gears.containsKey(pos)) {
                    gears.get(pos).add(num);
                }
            }
        });

        return gears.values().stream()
                .filter(adjNums -> adjNums.size() == 2)
                .mapToLong(nums -> nums.stream().reduce(1, (a, b) -> a * b))
                .reduce(0L, Long::sum);
    }

    private Map<Pair<Integer, Integer>, List<Integer>> extractGearPositions() {
        final Map<Pair<Integer, Integer>, List<Integer>> gears = new HashMap<>();
        for (int line = 1; line < schematic.size() - 1; line++) {
            for (int ch = 1; ch < schematic.get(line).length() - 1; ch++) {
                final char currentChar = schematic.get(line).charAt(ch);
                if (currentChar == '*') {
                    gears.put(Pair.of(line, ch), new ArrayList<>());
                }
            }
        }
        return gears;
    }

    private void forEachNumberOnSchematic(BiConsumer<Integer, List<Pair<Integer, Integer>>> fn) {
        for (int line = 1; line < schematic.size() - 1; line++) {
            final List<String> numbersOnLine = Arrays.stream(schematic.get(line).split("\\D")).filter(it -> !it.isEmpty()).toList();
            int lastIdx = 0;
            for (String num : numbersOnLine) {
                final int start = schematic.get(line).indexOf(num, lastIdx);
                final int end = start + num.length() - 1;

                // front and back
                List<Pair<Integer, Integer>> adjacentPositions = new ArrayList<>(List.of(
                        Pair.of(line - 1, start - 1),
                        Pair.of(line, start - 1),
                        Pair.of(line + 1, start - 1),
                        Pair.of(line - 1, end + 1),
                        Pair.of(line, end + 1),
                        Pair.of(line + 1, end + 1)
                ));

                // sides
                for (int i = start; i <= end; i++) {
                    adjacentPositions.add(Pair.of(line - 1, i));
                    adjacentPositions.add(Pair.of(line + 1, i));
                }

                fn.accept(Integer.parseInt(num), adjacentPositions);

                lastIdx = end + 1;
            }
        }
    }
}
