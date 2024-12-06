package me.frenz.day00;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day00Test {

    public static final String EXAMPLE = """
            """;

    @Test
    void testExampleA() {
        var day = new Day00(EXAMPLE.lines().toList());
        var result = day.part1();
        assertEquals(0L, result);
    }


    @Test
    void testExampleB() {
        var day = new Day00(EXAMPLE.lines().toList());
        var result = day.part2();
        assertEquals(0L, result);
    }


}
