
package me.frenz.day12;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day12Test {

    final List<String> input = """
        """.lines().toList();

    @Test
    void testExampleA() {
        assertEquals(-1, new Day12(input).part1());
    }

    @Test
    void testExampleB() {
        assertEquals(-1, new Day12(input).part2());
    }
}
