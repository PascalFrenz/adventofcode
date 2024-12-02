package me.frenz.day01;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day03Test {

    private static final List<String> EXAMPLE_INPUT = """
            """.lines().toList();

    private Day03 day;

    @BeforeEach
    void setUp() {
        this.day = new Day03(EXAMPLE_INPUT);
    }

    @Test
    void testExampleA() {
        var result = day.part1();
        assertEquals(0, result);
    }

    @Test
    void testExampleB() {
        var result = day.part2();
        assertEquals(0, result);
    }


}
