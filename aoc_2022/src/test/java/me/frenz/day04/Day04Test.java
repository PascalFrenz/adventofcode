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
                """;
        dut = new Day04(EXAMPLE_INPUT.lines().collect(Collectors.toList()));
    }

    @Test
    void part1() {
        assertEquals(-1, dut.part1());
    }

    @Test
    void part2() {
        assertEquals(-1, dut.part2());
    }

}
