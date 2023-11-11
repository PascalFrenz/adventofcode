package me.frenz.day05;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Day05Test {

    @Test
    void testReadBoardingPass_GivenExamples() {
        assertEquals(new Day05.BoardingPass(44, 5), Day05.BoardingPass.from("FBFBBFFRLR"));
        assertEquals(new Day05.BoardingPass(70, 7), Day05.BoardingPass.from("BFFFBBFRRR"));
        assertEquals(new Day05.BoardingPass(14, 7), Day05.BoardingPass.from("FFFBBBFRRR"));
        assertEquals(new Day05.BoardingPass(102, 4), Day05.BoardingPass.from("BBFFBBFRLL"));
    }

    @Test
    void testReadBoardingPass_InputValidation() {
        assertDoesNotThrow(() -> {
            Day05.BoardingPass.from("BFFFBBFRRR");
        });
        assertDoesNotThrow(() -> {
            Day05.BoardingPass.from("FFFBBBFRRR");
        });
        assertDoesNotThrow(() -> {
            Day05.BoardingPass.from("BBFFBBFRLL");
        });

        assertThrows(IllegalArgumentException.class, () -> Day05.BoardingPass.from("BFFFBBFRRa")); // wrong char in last 3
        assertThrows(IllegalArgumentException.class, () -> Day05.BoardingPass.from("FFFBBFRRL")); // too short
        assertThrows(IllegalArgumentException.class, () -> Day05.BoardingPass.from("BFFFBBFRBL")); // wrong char in last 3
        assertThrows(IllegalArgumentException.class, () -> Day05.BoardingPass.from("BFFFBBFRRLL")); // too long
        assertThrows(IllegalArgumentException.class, () -> Day05.BoardingPass.from("BFFFBBFRRLa")); // too long and wrong char
    }

    @Test
    void quickBitShiftTest() {
        int a = 127;
        int b = 0;
        System.out.printf("(%d, %d)\n", b, a);
        a -= a / 2 + 1;
        System.out.printf("(%d, %d)\n", b, a);
        b = a / 2 + 1;
        System.out.printf("(%d, %d)\n", b, a);
        a -= (a - b) / 2 + 1;
        System.out.printf("(%d, %d)\n", b, a);
        b += (a - b) / 2 + 1;
        System.out.printf("(%d, %d)\n", b, a);
    }
}
