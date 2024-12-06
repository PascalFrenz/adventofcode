package me.frenz.day01;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.*;

class Day04Test {

    public static final String EXAMPLE = """
            MMMSXXMASM
            MSAMXMSMSA
            AMXSXMAAMM
            MSAMASMSMX
            XMASAMXAMM
            XXAMMXXAMA
            SMSMSASXSS
            SAXAMASAAA
            MAMMMXMMMM
            MXMXAXMASX
            """;

    static Stream<Arguments> provideTestCasesForPart1() {
        return Stream.of(
                of(EXAMPLE, 18L),
                of("""
                        XMAS
                        """, 1L),
                of("""
                        X...
                        .M..
                        ..A.
                        ...S
                        """, 1L),
                of("""
                        S...
                        .A..
                        ..M.
                        ...X
                        """, 1L),
                of("""
                        ...X
                        ..M.
                        .A..
                        S...
                        """, 1L),
                of("""
                        ...S
                        ..A.
                        .M..
                        X...
                        """, 1L),
                of("""
                        SAMX
                        """, 1L),
                of("""
                        S...
                        A...
                        M...
                        X...
                        """, 1L),
                of("""
                        X...
                        M...
                        A...
                        S...
                        """, 1L)
        );
    }

    @ParameterizedTest
    @MethodSource("provideTestCasesForPart1")
    void testPart1(String input, long expected) {
        var day = new Day04(input.lines().toList());
        var result = day.part1();
        assertEquals(expected, result);
    }


    @Test
    void testExampleB() {
        var day = new Day04(EXAMPLE.lines().toList());
        var result = day.part2();
        assertEquals(9L, result);
    }


}
