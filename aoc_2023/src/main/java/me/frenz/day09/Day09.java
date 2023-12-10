
package me.frenz.day09;

import me.frenz.Day;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day09 extends Day<Long, Long> {

    public Day09(List<String> input) {
        super(input);
    }

    @Override
    protected Long part1() {
        return input.stream()
                .map(line -> Arrays.stream(line.split(" ")).mapToLong(Long::valueOf).boxed().toList())
                .mapToLong(Day09::extrapolateNextValue)
                .sum();
    }

    @Override
    protected Long part2() {
        return input.stream()
                .map(line -> Arrays.stream(line.split(" ")).mapToLong(Long::valueOf).boxed().toList())
                .mapToLong(Day09::extrapolatePreviousValue)
                .sum();
    }

    private static long extrapolatePreviousValue(List<Long> sequence) {
        final List<List<Long>> diffHistory = buildDifferenceHistory(sequence);
        long extrapolated = 0L;
        for (int i = diffHistory.size() - 2; i >= 0; i--) {
            final List<Long> currDiff = diffHistory.get(i);
            extrapolated = currDiff.getFirst() - extrapolated;
        }
        return extrapolated;
    }

    private static long extrapolateNextValue(List<Long> sequence) {
        final List<List<Long>> diffHistory = buildDifferenceHistory(sequence);
        long extrapolated = 0L;
        for (int i = diffHistory.size() - 2; i >= 0; i--) {
            final List<Long> currDiff = diffHistory.get(i);
            extrapolated = currDiff.getLast() + extrapolated;
        }
        return extrapolated;
    }

    private static List<List<Long>> buildDifferenceHistory(List<Long> sequence) {
        final List<List<Long>> diffHistory = new ArrayList<>(2);
        diffHistory.add(sequence);

        boolean done = false;
        while (!done) {
            final List<Long> currSequence = diffHistory.getLast();
            final List<Long> differences = new ArrayList<>(1);
            for (int i = 0; i < currSequence.size() - 1; i++) {
                differences.add(currSequence.get(i + 1) - currSequence.get(i));
            }
            done = differences.stream().allMatch(it -> it == 0);
            diffHistory.addLast(differences);
        }
        return diffHistory;
    }

}
