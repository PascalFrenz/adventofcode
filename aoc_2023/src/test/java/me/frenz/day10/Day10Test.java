
package me.frenz.day10;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day10Test {

    @Test
    void testExampleA() {
        final List<String> input = """
                .....
                .S-7.
                .|.|.
                .L-J.
                .....
                """.lines().toList();
        assertEquals(4, new Day10(input).part1());
    }

    @Test
    void testExampleAB() {
        final List<String> input = """
                ..F7.
                .FJ|.
                SJ.L7
                |F--J
                LJ...
                """.lines().toList();
        assertEquals(8, new Day10(input).part1());
    }

    @Test
    void testExampleB() {
        final List<String> input = """
                ...........
                .S-------7.
                .|F-----7|.
                .||.....||.
                .||.....||.
                .|L-7.F-J|.
                .|..|.|..|.
                .L--J.L--J.
                ...........
                """.lines().toList();
        assertEquals(4, new Day10(input).part2());
    }

    @Test
    void testExampleB2() {
        final List<String> input = """
                .F----7F7F7F7F-7....
                .|F--7||||||||FJ....
                .||.FJ||||||||L7....
                FJL7L7LJLJ||LJ.L-7..
                L--J.L7...LJS7F-7L7.
                ....F-J..F7FJ|L7L7L7
                ....L7.F7||L7|.L7L7|
                .....|FJLJ|FJ|F7|.LJ
                ....FJL-7.||.||||...
                ....L---J.LJ.LJLJ...
                """.lines().toList();
        assertEquals(8, new Day10(input).part2());
    }
}
