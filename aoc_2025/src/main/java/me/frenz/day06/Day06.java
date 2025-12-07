
package me.frenz.day06;

import me.frenz.Day;

import java.util.*;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
        final var operatorString = input.getLast().toCharArray();
        final var inputWithoutOperators = input.subList(0, input.size() - 1);

        final var operators = extractOperators(operatorString);
        final var columns = columnizeUsing(inputWithoutOperators, new ArrayList<>(operators.keySet()));
        final var results = computeResultsByColumn(columns, operators);

        return totalSum(results);
    }

    private static Long totalSum(HashMap<Integer, Long> results) {
        return results.values().stream().reduce(0L, Long::sum);
    }

    private HashMap<Integer, Long> computeResultsByColumn(Map<Integer, List<String>> columns, Map<Integer, BiFunction<Integer, Integer, Long>> operators) {
        final var results = new HashMap<Integer, Long>();
        for (var column : columns.entrySet()) {
            List<Long> pivot = pivot(column.getValue()).stream().mapToLong(Long::parseLong).boxed().toList();
            final BiFunction<Integer, Integer, Long> operation = operators.get(column.getKey());
            final Optional<Long> result = pivot.stream().reduce((acc, l) -> operation.apply(Math.toIntExact(acc), Math.toIntExact(l)));
            if (result.isPresent()) {
                results.put(column.getKey(), result.get());
            } else {
                throw new IllegalArgumentException("should not happen!");
            }
        }
        return results;
    }

    Map<Integer, List<String>> columnizeUsing(List<String> input, List<Integer> splitPoints) {
        final var columns = new HashMap<Integer, List<String>>();
        for (var line : input) {
            splitPoints.sort(Integer::compareTo); // in case they were not sorted yet
            for (int i = 0; i < splitPoints.size(); i++) {
                final int splitAt = splitPoints.get(i);
                final var toAdd = i < splitPoints.size() - 1
                        ? line.substring(splitAt, splitPoints.get(i + 1) - 1) // not the end of the list
                        : line.substring(splitAt); // last element of the list

                columns.compute(splitPoints.get(i), (_, val) -> {
                    if (val == null) {
                        return new ArrayList<>(List.of(toAdd));
                    } else {
                        val.add(toAdd);
                        return val;
                    }
                });
            }
        }
        return columns;
    }

    static Map<Integer, BiFunction<Integer, Integer, Long>> extractOperators(char[] operatorString) {
        final Map<Integer, BiFunction<Integer, Integer, Long>> operators = new HashMap<>();
        for (int i = 0; i < operatorString.length; i++) {
            switch (operatorString[i]) {
                case '+':
                    operators.put(i, (a, b) -> (long) (a + b));
                    break;
                case '*':
                    operators.put(i, (a, b) -> (long) a * b);
                    break;
                case ' ':
                    // skip spaces
                    break;
                default:
                    throw new IllegalArgumentException("Unknown operator: " + operatorString[i]);
            }
        }
        return operators;
    }

    List<String> pivot(List<String> input) {
        if (input.size() == 1 && input.getFirst().length() == 1) {
            return input;
        }
        if (input.stream().allMatch(String::isBlank)) {
            return input;
        }

        final var maxOpt = input.stream().map(String::length).max(Integer::compareTo);
        final var pivoted = IntStream.range(0, maxOpt.get())
                .mapToObj(_ -> "")
                .collect(Collectors.toCollection(ArrayList::new));

        for (String line : input) {
            for (int i = 0; i < line.length(); i++) {
                if (pivoted.get(i) != null) {
                    pivoted.set(i, (pivoted.get(i) + line.charAt(i)).trim());
                } else {
                    pivoted.add(String.valueOf(line.charAt(i)));
                }
            }
        }

        return pivoted;
    }

}
