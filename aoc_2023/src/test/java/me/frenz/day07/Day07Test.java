
package me.frenz.day07;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day07Test {

    final List<String> input = """
            32T3K 765
            T55J5 684
            KK677 28
            KTJJT 220
            QQQJA 483
            """.lines().toList();

    @Test
    void testExampleA() {
        assertEquals(6440, new Day07(input).part1());
    }

    @Test
    void testExampleB() {
        assertEquals(5905, new Day07(input).part2());
    }
}
