package me.frenz.day01;

import org.junit.jupiter.api.Test;

import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day01Test {

    private final String EXAMPLE_INPUT = """
                                        1000
                                        2000
                                        3000
                                            
                                        4000
                                            
                                        5000
                                        6000
                                            
                                        7000
                                        8000
                                        9000
                                            
                                        10000
                                        """;

    @Test
    void part1() {
        final Day01 day01 = new Day01(EXAMPLE_INPUT.lines().collect(Collectors.toList()));
        final Integer actual = day01.part1();
        assertEquals(24000, actual);
    }

    @Test
    void part2() {
        final Day01 day01 = new Day01(EXAMPLE_INPUT.lines().collect(Collectors.toList()));
        final Integer actual = day01.part2();
        assertEquals(45000, actual);
    }
}
