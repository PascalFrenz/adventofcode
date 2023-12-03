
package me.frenz.day03;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day03Test {

    final List<String> input = """
            467..114..
            ...*......
            ..35..633.
            ......#...
            617*......
            .....+.58.
            ..592.....
            ......755.
            ...$.*....
            .664.598..
            """.lines().toList();

    @Test
    void testExampleA() {
        assertEquals(4361, new Day03(input).part1());
    }

    @Test
    void testSameNumberOnSameLine() {
        final List<String> input = """
            1.*.1
            *...1
            """.lines().toList();
        assertEquals(1, new Day03(input).part1());
    }

    @Test
    void testExampleB() {
        assertEquals(467835, new Day03(input).part2());
    }



    @Test
    void testGears() {
        final List<String> input = """
            .1*1.
            .....
            ..1..
            .1*..
            .....
            ..1..
            ..*..
            ..1..
            .....
            ..1..
            ..*1.
            .....
            .1*..
            ..1..
            .....
            ..*1.
            ..1..
            .....
            .1.1.
            ..*..
            .....
            .11..
            ..*..
            .....
            ..11.
            ..*..
            .....
            .1...
            .1*..
            .....
            ...1.
            ..*1.
            .....
            ..*..
            .1.1.
            .....
            ..*..
            .11..
            .....
            ..*..
            ..11.
            .....
            ..*1.
            ...1.
            .....
            .1*..
            .1...
            """.lines().toList();
        assertEquals(16, new Day03(input).part2());
    }

    @Test
    void name() {
        List<String> input;

        input = """
            .1*1.
            """.lines().toList();
        assertEquals(1, new Day03(input).part2());

        input = """
            ..1..
            .1*..
            """.lines().toList();
        assertEquals(1, new Day03(input).part2());

        input = """
            ..1..
            ..*..
            ..1..
            """.lines().toList();
        assertEquals(1, new Day03(input).part2());

        input = """
            ..1..
            ..*1.
            """.lines().toList();
        assertEquals(1, new Day03(input).part2());

        input = """
            .1*..
            ..1..
            """.lines().toList();
        assertEquals(1, new Day03(input).part2());

        input = """
            .1.1.
            ..*..
            """.lines().toList();
        assertEquals(1, new Day03(input).part2());

        input = """
            .1...
            .1*..
            """.lines().toList();
        assertEquals(1, new Day03(input).part2());

        input = """
            ...1.
            ..*1.
            """.lines().toList();
        assertEquals(1, new Day03(input).part2());

        input = """
            ..*..
            .1.1.
            """.lines().toList();
        assertEquals(1, new Day03(input).part2());

        input = """
            ..*1.
            ...1.
            """.lines().toList();
        assertEquals(1, new Day03(input).part2());

        input = """
            .1*..
            .1...
            """.lines().toList();
        assertEquals(1, new Day03(input).part2());
    }
}
