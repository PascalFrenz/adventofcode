package me.frenz.day01;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day01Test {

    public static final List<String> INPUT = """
            3   4
            4   3
            2   5
            1   3
            3   9
            3   3
            """.lines().toList();

    @Test
    void testExampleA() {
        var result = new Day01(INPUT).part1();
        assertEquals(11, result);
    }

    @Test
    void testExampleB() {
        var result = new Day01(INPUT).part2();
        assertEquals(31, result);
    }


}
