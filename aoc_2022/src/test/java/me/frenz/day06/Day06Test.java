package me.frenz.day06;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;


class Day06Test {

    private Day06 dut;

    @BeforeEach
    void setUp() {
        String EXAMPLE_INPUT = """
                A Y
                B X
                C Z
                """;
        dut = new Day06(EXAMPLE_INPUT.lines().collect(Collectors.toList()));
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