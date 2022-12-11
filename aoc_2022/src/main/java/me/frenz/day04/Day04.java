package me.frenz.day04;

import me.frenz.Day;
import me.frenz.Pair;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day04 extends Day<Long, Long> {

    private static final Function<String, Pair<Integer>> fromSection = section -> {
        final String[] split = section.split("-");
        return new Pair<>(Integer.valueOf(split[0]), Integer.valueOf(split[1]));
    };

    public Day04(List<String> input) {
        super(input);
    }

    @Override
    protected Long part1() {
        return input.stream()
                .map(Day04::putSectionsIntoSets)
                .filter(Day04::testForCompleteSectionOverlap)
                .count();
    }

    @Override
    protected Long part2() {
        return input.stream()
                .map(Day04::putSectionsIntoSets)
                .map(Day04::intersectSections)
                .filter(integers -> !integers.isEmpty())
                .count();
    }

    private static Pair<Set<Integer>> putSectionsIntoSets(String line) {
        final String[] sections = line.split(",");
        final Pair<Integer> first = fromSection.apply(sections[0]);
        final Pair<Integer> second = fromSection.apply(sections[1]);
        final Set<Integer> firstSet = IntStream.rangeClosed(first.left(), first.right()).boxed().collect(Collectors.toSet());
        final Set<Integer> secondSet = IntStream.rangeClosed(second.left(), second.right()).boxed().collect(Collectors.toSet());
        return new Pair<>(firstSet, secondSet);
    }

    private static boolean testForCompleteSectionOverlap(Pair<Set<Integer>> sections) {
        final boolean leftContainsRight = sections.left().containsAll(sections.right());
        final boolean rightContainsLeft = sections.right().containsAll(sections.left());
        return leftContainsRight || rightContainsLeft;
    }

    private static HashSet<Integer> intersectSections(Pair<Set<Integer>> sets) {
        final HashSet<Integer> intersection = new HashSet<>(sets.left());
        intersection.retainAll(sets.right());
        return intersection;
    }
}
