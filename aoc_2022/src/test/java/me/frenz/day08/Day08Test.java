package me.frenz.day08;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;


class Day08Test {

    private Day08 dut;

    @BeforeEach
    void setUp() {
        String EXAMPLE_INPUT = """
                30373
                25512
                65332
                33549
                35390
                """;
        dut = new Day08(EXAMPLE_INPUT.lines().collect(Collectors.toList()));
    }

    @Test
    void part1() {
        assertEquals(21, dut.part1());
    }

    @Test
    void part2() {
        assertEquals(8, dut.part2());
    }
}
