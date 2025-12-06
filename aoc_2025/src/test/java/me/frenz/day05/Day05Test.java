
package me.frenz.day05;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day05Test {

    final List<String> input = """
        3-5
        10-14
        16-20
        12-18
        
        1
        5
        8
        11
        17
        32
        """.lines().toList();

    @Test
    void testExampleA() {
        assertEquals(3L, new Day05(input).part1());
    }

    @Test
    void testExampleB() {
        assertEquals(14, new Day05(input).part2());
    }
}
