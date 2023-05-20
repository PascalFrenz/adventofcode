package me.frenz.day12;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;


class Day12Test {

    private Day12 dut;

    @BeforeEach
    void setUp() {
        String EXAMPLE_INPUT = """
                Sabqponm
                abcryxxl
                accszExk
                acctuvwj
                abdefghi
                """;
        dut = new Day12(EXAMPLE_INPUT.lines().collect(Collectors.toList()));
    }

    @Test
    void part1() {
        assertEquals(31, dut.part1());
    }

    @Test
    void part2() {
        assertEquals(29, dut.part2());
    }
}
