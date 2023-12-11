
package me.frenz.day11;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertLinesMatch;

class Day11Test {

    final List<String> input = """
            ...#......
            .......#..
            #.........
            ..........
            ......#...
            .#........
            .........#
            ..........
            .......#..
            #...#.....
            """.lines().toList();

    @Test
    void testExpandMap() {
        List<String> expected = """
                ..|#.|..|.
                ..|..|.#|.
                #.|..|..|.
                --|--|--|-
                ..|..|#.|.
                .#|..|..|.
                ..|..|..|#
                --|--|--|-
                ..|..|.#|.
                #.|.#|..|.
                """.lines().toList();

        assertLinesMatch(expected, new Day11(input).expandMap());
    }

    @Test
    void testExampleA() {
        assertEquals(374, new Day11(input).part1());
    }

    @Test
    void testExampleB() {
        assertEquals(8410, new Day11(input, 100).part2());
        assertEquals(1030, new Day11(input, 10).part2());
    }
}
