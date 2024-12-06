package me.frenz.day05;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day05Test {

    public static final String EXAMPLE = """
            """;

    @Test
    void testExampleA() {
        var day = new Day05(EXAMPLE.lines().toList());
        var result = day.part1();
        assertEquals(0L, result);
    }


    @Test
    void testExampleB() {
        var day = new Day05(EXAMPLE.lines().toList());
        var result = day.part2();
        assertEquals(0L, result);
    }


}
