
package me.frenz.day03;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day03Test {

    final List<String> input = """
        987654321111111
        811111111111119
        234234234234278
        818181911112111
        """.lines().toList();

    @Test
    void testExampleA() {
        assertEquals(98L, new Day03(List.of("987654321111111")).part1());
        assertEquals(89L, new Day03(List.of("811111111111119")).part1());
        assertEquals(78L, new Day03(List.of("234234234234278")).part1());
        assertEquals(92L, new Day03(List.of("818181911112111")).part1());
        assertEquals(357L, new Day03(input).part1());
    }

    @Test
    void testExampleB() {
        assertEquals(434234234278L, new Day03(List.of("234234234234278")).part2());
        assertEquals(987654321111L, new Day03(List.of("987654321111111")).part2());
        assertEquals(811111111119L, new Day03(List.of("811111111111119")).part2());
        assertEquals(888911112111L, new Day03(List.of("818181911112111")).part2());
        assertEquals(3121910778619L, new Day03(input).part2());
    }
}
