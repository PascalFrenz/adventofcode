
package me.frenz.day05;

import lombok.NonNull;
import me.frenz.Day;

import java.util.*;
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
        final var freshIds = input.stream()
                .takeWhile(it -> !it.isEmpty())
                .map(Interval::from)
                .sorted()
                .collect(Collectors.toCollection(ArrayList::new));
        final var joinedFreshIds = new Stack<Interval>();
        joinedFreshIds.add(freshIds.removeFirst());
        while (!freshIds.isEmpty()) {
            final var current = freshIds.removeFirst();
            final var joinedOpt = joinedFreshIds.peek().join(current);
            if (joinedOpt.isPresent()) {
                joinedFreshIds.pop();
                joinedFreshIds.add(joinedOpt.get());
            } else {
                joinedFreshIds.add(current);
            }
        }
        return joinedFreshIds.stream().mapToLong(Interval::count).sum();
    }


    record Interval(long start, long end) implements Comparable<Interval> {

        static Interval from(String string) {
            final var split = string.split("-");
            final var start = Long.parseLong(split[0]);
            final var end = Long.parseLong(split[1]);
            return new Interval(start, end);
        }

        boolean contains(long value) {
            return value >= start && value <= end;
        }

        Optional<Interval> join(Interval other) {
            if (other.contains(start) && other.contains(end)) {
                return Optional.of(other);
            } else if (other.contains(start) && this.contains(other.end)) {
                return Optional.of(new Interval(other.start, this.end));
            } else if (this.contains(other.start) && other.contains(end)) {
                return Optional.of(new Interval(this.start, other.end));
            } else if (this.contains(other.start) && this.contains(other.end)) {
                return Optional.of(this);
            }

            return Optional.empty();
        }

        long count() {
            return end - start + 1;
        }

        @Override
        public int compareTo(@NonNull Interval other) {
            return Comparator
                    .comparingLong(Interval::start)
                    .thenComparing(Interval::end)
                    .compare(this, other);
        }
    }
}
