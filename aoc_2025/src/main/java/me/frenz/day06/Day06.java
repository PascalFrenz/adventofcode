
package me.frenz.day06;

import me.frenz.Day;

import java.util.Arrays;
import java.util.List;

public class Day06 extends Day<Long, Long> {

    public Day06(List<String> input) {
        super(input);
    }

    @Override
    protected Long part1() {
        final char[] operators = input.getLast().replaceAll(" +", "").toCharArray();
        final long[] results = new long[operators.length];
        for (String line : input.subList(0, input.size() - 1)) {
            final String[] numbers = Arrays.stream(line.split(" +")).filter(it -> !it.isBlank()).toArray(String[]::new);
            for (int i = 0; i < numbers.length; i++) {
                results[i] = switch (operators[i]) {
                    case '+' -> results[i] += Long.parseLong(numbers[i]);
                    case '*' -> results[i] = Long.parseLong(numbers[i]) * Math.max(1, results[i]);
                    default -> throw new IllegalArgumentException("Unknown operator: " + operators[i]);
                };
            }
        }
        return Arrays.stream(results).sum();
    }

    @Override
    protected Long part2() {
        return 0L;
    }

}
