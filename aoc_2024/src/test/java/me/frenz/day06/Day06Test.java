package me.frenz.day06;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day06Test {

    public static final String EXAMPLE = """
            ....#.....
            .........#
            ..........
            ..#.......
            .......#..
            ..........
            .#..^.....
            ........#.
            #.........
            ......#...
            """;

    @Test
    void testExampleA() {
        var day = new Day06(EXAMPLE.lines().toList());
        var result = day.part1();
        assertEquals(41L, result);
    }


    @Test
    void testExampleB() {
        var day = new Day06(EXAMPLE.lines().toList());
        var result = day.part2();
        assertEquals(6L, result);
    }


}
