package me.frenz.day01;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day01Test {

    private static final List<String> EXAMPLE_INPUT = """
            3   4
            4   3
            2   5
            1   3
            3   9
            3   3
            """.lines().toList();

    private Day01 day;

    @BeforeEach
    void setUp() {
        this.day = new Day01(EXAMPLE_INPUT);
    }

    @Test
    void testExampleA() {
        var result = day.part1();
        assertEquals(11, result);
    }

    @Test
    void testExampleB() {
        var result = day.part2();
        assertEquals(31, result);
    }


}
