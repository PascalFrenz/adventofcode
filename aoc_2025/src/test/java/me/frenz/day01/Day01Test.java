
package me.frenz.day01;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day01Test {

    final List<String> input = """
        L68
        L30
        R48
        L5
        R60
        L55
        L1
        L99
        R14
        L82
        """.lines().toList();

    @Test
    void testExampleA() {
        assertEquals(3, new Day01(input).part1());
    }

    @Test
    void testExampleB() {
        assertEquals(6, new Day01(input).part2());
    }

    @Test
    void shouldAllReturnOne() {
        assertEquals(1, new Day01(List.of("R50", "R50")).part2());
        assertEquals(1, new Day01(List.of("R50", "L50")).part2());
        assertEquals(1, new Day01(List.of("L50", "R50")).part2());
        assertEquals(1, new Day01(List.of("L50", "L50")).part2());
    }

    @Test
    void shouldAllReturnTwo() {
        assertEquals(2, new Day01(List.of("R150", "R50")).part2());
        assertEquals(2, new Day01(List.of("L150", "R50")).part2());
        assertEquals(2, new Day01(List.of("R150", "L50")).part2());
        assertEquals(2, new Day01(List.of("L150", "L50")).part2());
    }
}
