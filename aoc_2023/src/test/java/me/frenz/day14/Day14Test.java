
package me.frenz.day14;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day14Test {

    final List<String> input = """
        """.lines().toList();

    @Test
    void testExampleA() {
        assertEquals(-1, new Day14(input).part1());
    }

    @Test
    void testExampleB() {
        assertEquals(-1, new Day14(input).part2());
    }
}
