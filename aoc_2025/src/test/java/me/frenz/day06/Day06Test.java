
package me.frenz.day06;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day06Test {

    final List<String> input = """
        123 328  51 64\s
         45 64  387 23\s
          6 98  215 314
        *   +   *   + \s
        """.lines().toList();

    @Test
    void testExampleA() {
        assertEquals(4277556L, new Day06(input).part1());
    }

    @Test
    void testExampleB() {
        assertEquals(-1L, new Day06(input).part2());
    }
}
