package me.frenz.day05;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day05Test {

    private Day05 dut;

    @BeforeEach
    void setUp() {
        String EXAMPLE_INPUT = """
                """;
        dut = new Day05(EXAMPLE_INPUT.lines().collect(Collectors.toList()));
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
