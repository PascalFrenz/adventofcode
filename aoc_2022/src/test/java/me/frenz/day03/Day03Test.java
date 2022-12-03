package me.frenz.day03;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day03Test {
    private Day03 dut;

    @BeforeEach
    void setUp() {
        String EXAMPLE_INPUT = """
                vJrwpWtwJgWrhcsFMMfFFhFp
                jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL
                PmmdzqPrVvPwwTWBwg
                wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn
                ttgJtRGJQctTZtZT
                CrZsJsPPZsGzwwsLwLmpwMDw
                """;
        dut = new Day03(EXAMPLE_INPUT.lines().collect(Collectors.toList()));
    }

    @Test
    void part1() {
        assertEquals(157, dut.part1());
    }

    @Test
    void part2() {
        assertEquals(70, dut.part2());
    }
}
