package me.frenz.day07;

import me.frenz.Day;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day07 extends Day<Integer, Long> {

    public static final String SHINY_GOLD = "shiny gold";
    private final Map<String, Bag> bags;

    public Day07(List<String> input) {
        super(input);
        bags = getBagMap(input.stream());
    }

    @Override
    protected Integer part1() {
        return findBags(SHINY_GOLD, bags, new HashSet<>()).size();
    }

    @Override
    protected Long part2() {
        return countInnerBags(SHINY_GOLD, bags);
    }

    static Map<String, Bag> getBagMap(Stream<String> input) {
        return input.map(Bag::from).collect(Collectors.toMap(Bag::name, Function.identity()));
    }

    static Set<String> findBags(final String search, final Map<String, Bag> bags, final Set<String> result) {
        final Set<String> newBags = bags.values().stream()
                .map(bag -> bag.containsBag(search) ? bag.name() : null)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        for (String newBag : newBags) {
            result.addAll(findBags(newBag, bags, result));
        }

        result.addAll(newBags);

        return result;
    }

    static long countInnerBags(final String bag, final Map<String, Bag> bags) {
        final Bag current = bags.get(bag);
        return current.getInnerBags().stream()
                .map(bags::get)
                .mapToLong(b -> current.getCountFor(b.name()) + current.getCountFor(b.name()) * countInnerBags(b.name(), bags))
                .sum();
    }

}
