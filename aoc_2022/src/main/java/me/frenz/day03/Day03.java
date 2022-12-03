package me.frenz.day03;

import me.frenz.Day;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toSet;

public class Day03 extends Day<Integer, Integer> {

    public Day03(List<String> input) {
        super(input);
    }

    @Override
    protected Integer part1() {
        return input.stream()
                .map(Day03::calculateCompartmentIntersection)
                .flatMap(Day03::prioritizeItems)
                .reduce(Integer::sum)
                .orElse(-1);
    }

    @Override
    protected Integer part2() {
        return getGroupBadges()
                .stream()
                .flatMap(Day03::prioritizeItems)
                .reduce(Integer::sum)
                .orElse(-1);
    }

    private List<Set<Integer>> getGroupBadges() {
        final List<Set<Integer>> badges = new ArrayList<>();
        for (int i = 0; i < input.size() - 2; ) {
            final Set<Integer> first = input.get(i++).chars().boxed().collect(toSet());
            final Set<Integer> second = input.get(i++).chars().boxed().collect(toSet());
            final Set<Integer> third = input.get(i++).chars().boxed().collect(toSet());
            
            final HashSet<Integer> intersection = new HashSet<>(first);
            intersection.retainAll(second);
            intersection.retainAll(third);
            badges.add(intersection);
        }
        return badges;
    }

    private static Stream<Integer> prioritizeItems(Set<Integer> intersection) {
        return intersection.stream()
                .map(charVal -> {
                    if (Character.isLowerCase(charVal)) {
                        return charVal - 'a' + 1;
                    } else {
                        return charVal - 'A' + 27;
                    }
                });
    }

    private static Set<Integer> calculateCompartmentIntersection(String rucksack) {
        final int compartmentLength = rucksack.length() / 2;

        final Set<Integer> firstCompartment = rucksack.substring(0, compartmentLength).chars().boxed().collect(toSet());
        final Set<Integer> secondCompartment = rucksack.substring(compartmentLength).chars().boxed().collect(toSet());

        final Set<Integer> intersection = new HashSet<>(firstCompartment);
        intersection.retainAll(secondCompartment);
        return intersection;
    }
}
