package me.frenz.day09;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day09Test {
    private Day09 dut;

    @BeforeEach
    void setUp() {
        String EXAMPLE_INPUT = """
                R 4
                U 4
                L 3
                D 1
                R 4
                D 1
                L 5
                R 2
                """;
        dut = new Day09(EXAMPLE_INPUT.lines().collect(Collectors.toList()));
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
