package aoc2020;

import Day7.Bag;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day7Test {

    Stream<String> exampleInput;

    @BeforeEach
    void setUp() {
        exampleInput = Stream.<String>builder()
                .add("light red bags contain 1 bright white bag, 2 muted yellow bags.")
                .add("dark orange bags contain 3 bright white bags, 4 muted yellow bags.")
                .add("bright white bags contain 1 shiny gold bag.")
                .add("muted yellow bags contain 2 shiny gold bags, 9 faded blue bags.")
                .add("shiny gold bags contain 1 dark olive bag, 2 vibrant plum bags.")
                .add("dark olive bags contain 3 faded blue bags, 4 dotted black bags.")
                .add("vibrant plum bags contain 5 faded blue bags, 6 dotted black bags.")
                .add("faded blue bags contain no other bags.")
                .add("dotted black bags contain no other bags.")
                .build();
    }

    @Test
    void testFindBag() {
        final Map<String, Bag> bagMap = Day7.getBagMap(exampleInput);
        final Set<String> actual = Day7.findBags("shiny gold", bagMap, new HashSet<>());
        assertEquals(4, actual.size());
    }

    @Test
    void testCountInnerBags() {
        final Map<String, Bag> bagMap = Day7.getBagMap(exampleInput);
        final long actual = Day7.countInnerBags("shiny gold", bagMap);
        assertEquals(32, actual);
    }
}