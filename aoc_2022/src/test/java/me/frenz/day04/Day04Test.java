package me.frenz.day04;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day04Test {

    private Day04 dut;

    @BeforeEach
    void setUp() {
        String EXAMPLE_INPUT = """
                2-4,6-8
                2-3,4-5
                5-7,7-9
                2-8,3-7
                6-6,4-6
                2-6,4-8
                """;
        dut = new Day04(EXAMPLE_INPUT.lines().collect(Collectors.toList()));
    }

    @Test
    void part1() {
        assertEquals(2, dut.part1());
    }

    @Test
    void part2() {
        assertEquals(4, dut.part2());
    }

}
