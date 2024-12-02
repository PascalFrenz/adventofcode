package me.frenz.day01;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day02Test {

    private static final List<String> EXAMPLE_INPUT = """
            7 6 4 2 1
            1 2 7 8 9
            9 7 6 2 1
            1 3 2 4 5
            8 6 4 4 1
            1 3 6 7 9
            """.lines().toList();

    private Day02 day;

    @BeforeEach
    void setUp() {
        this.day = new Day02(EXAMPLE_INPUT);
    }

    @Test
    void testExampleA() {
        var result = day.part1();
        assertEquals(2, result);
    }

    @Test
    void testExampleB() {
        var result = day.part2();
        assertEquals(4, result);
    }


}
