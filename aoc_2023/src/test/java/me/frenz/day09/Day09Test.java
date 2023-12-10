
package me.frenz.day09;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day09Test {

    final List<String> input = """
            0 3 6 9 12 15
            1 3 6 10 15 21
            10 13 16 21 30 45
            """.lines().toList();

    @Test
    void testExampleA() {
        assertEquals(114, new Day09(input).part1());
    }

    @Test
    void testExampleB() {
        assertEquals(2, new Day09(input).part2());
    }
}
