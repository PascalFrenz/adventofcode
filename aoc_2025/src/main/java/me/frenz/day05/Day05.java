
package me.frenz.day05;

import me.frenz.Day;

import java.util.List;
import java.util.stream.Collectors;

public class Day05 extends Day<Long, Long> {

    public Day05(List<String> input) {
        super(input);
    }

    @Override
    protected Long part1() {
        final var freshIds = input.stream().takeWhile(it -> !it.isEmpty()).toList();
        final var ingredients = input.stream().dropWhile(it -> !it.isEmpty()).filter(it -> !it.isEmpty()).toList();

        final var ids = freshIds.stream()
                .map(Interval::from)
                .collect(Collectors.toSet());

        return ingredients.stream()
                .mapToLong(Long::valueOf)
                .filter(it -> ids.stream().anyMatch(set -> set.contains(it)))
                .count();
    }

    @Override
    protected Long part2() {
        return 0L;
    }


    record Interval(long start, long end) {

        static Interval from(String string) {
            final var split = string.split("-");
            final var start = Long.parseLong(split[0]);
            final var end = Long.parseLong(split[1]);
            return new Interval(start, end);
        }

        boolean contains(long value) {
            return value >= start && value <= end;
        }
    }
}
