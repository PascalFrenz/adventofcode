
package me.frenz.day02;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day02Test {

    final List<String> input = "11-22,95-115,998-1012,1188511880-1188511890,222220-222224,1698522-1698528,446443-446449,38593856-38593862,565653-565659,824824821-824824827,2121212118-2121212124".lines().toList();

    @Test
    void testExampleA() {
        assertEquals(1227775554L, new Day02(input).part1());
    }

    @Test
    void testExampleB() {
        assertEquals(4174379265L, new Day02(input).part2());
    }
}
