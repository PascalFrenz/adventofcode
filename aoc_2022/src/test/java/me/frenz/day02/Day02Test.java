package me.frenz.day02;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;


class Day02Test {

    private Day02 dut;

    @BeforeEach
    void setUp() {
        String EXAMPLE_INPUT = """
                A Y
                B X
                C Z
                """;
        dut = new Day02(EXAMPLE_INPUT.lines().collect(Collectors.toList()));
    }

    @Test
    void part1() {
        assertEquals(15, dut.part1());
    }

    @Test
    void part2() {
        assertEquals(12, dut.part2());
    }
}