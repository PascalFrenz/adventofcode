
package me.frenz.day14;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day14Test {

    final List<String> input = """
        O....#....
        O.OO#....#
        .....##...
        OO.#O....O
        .O.....O#.
        O.#..O.#.#
        ..O..#O..O
        .......O..
        #....###..
        #OO..#....
        """.lines().toList();

    @Test
    void testExampleA() {
        assertEquals(136, new Day14(input).part1());
    }

    @Test
    void testExampleB() {
        assertEquals(64, new Day14(input).part2());
    }
}
