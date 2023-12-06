
package me.frenz.day06;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day06Test {

    final List<String> input = """
            Time:      7  15   30
            Distance:  9  40  200
            """.lines().toList();

    @Test
    void testExampleA() {
        assertEquals(288, new Day06(input).part1());
    }

    @Test
    void testExampleB() {
        assertEquals(71503, new Day06(input).part2());
    }
}
