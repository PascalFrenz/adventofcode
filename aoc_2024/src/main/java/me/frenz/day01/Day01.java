package me.frenz.day01;

import me.frenz.Day;
import me.frenz.Pair;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day01 extends Day<Integer, Integer> {

    public Day01(List<String> input) {
        super(input);
    }

    @Override
    protected Integer part1() {
        List<Pair<Integer, Integer>> numbers = parseInputToPairs();

        List<Integer> left = numbers.stream().map(Pair::left).sorted().toList();
        List<Integer> right = numbers.stream().map(Pair::right).sorted().toList();

        if (left.size() != right.size()) {
            throw new IllegalArgumentException("Left and right sides must have the same size");
        }

        int sum = 0;
        for (int i = 0; i < left.size(); i++) {
            sum += Math.abs(right.get(i) - left.get(i));
        }

        return sum;
    }

    @Override
    protected Integer part2() {
        var numbers = parseInputToPairs();

        Map<Integer, Integer> intOccurencesInRight = new HashMap<>();
        for (var pair : numbers ) {
            intOccurencesInRight.compute(pair.right(), (k, v) -> v == null ? 1 : v + 1);
        }

        int sum = 0;
        for (var pair : numbers) {
            var multiplier = intOccurencesInRight.getOrDefault(pair.left(), 0);
            sum += pair.left() * multiplier;
        }
        return sum;
    }

    private List<Pair<Integer, Integer>> parseInputToPairs() {
        return input.stream()
                .map(s -> s.split("\\s+"))
                .map(s -> new Pair<>(Integer.parseInt(s[0]), Integer.parseInt(s[1])))
                .toList();
    }
}
