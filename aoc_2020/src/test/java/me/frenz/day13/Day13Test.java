package me.frenz.day13;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day13Test {


    @Test
    void testExample1() {
        final List<String> input = """
                939
                7,13,x,x,59,x,31,19
                """.lines().toList();

        final Day13 day = new Day13(input);
        assertEquals(295, day.part1());
    }


    @Test
    void testExample2() {
        final List<String> input = """
                939
                7,13,x,x,59,x,31,19
                """.lines().toList();

        final Day13 day = new Day13(input);
        assertEquals(1068781, day.part2());
    }
}
