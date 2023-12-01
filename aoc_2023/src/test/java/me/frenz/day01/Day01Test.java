package me.frenz.day01;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day01Test {

    @Test
    void testExampleA() {
        final List<String> input = """
                1abc2
                pqr3stu8vwx
                a1b2c3d4e5f
                treb7uchet
                """.lines().toList();

        assertEquals(142, new Day01(input).part1());
    }

    @Test
    void testExampleB() {
        final List<String> input = """
                two1nine
                eightwothree
                abcone2threexyz
                xtwone3four
                4nineeightseven2
                zoneight234
                7pqrstsixteen
                """.lines().toList();

        assertEquals(281, new Day01(input).part2());
    }


}
