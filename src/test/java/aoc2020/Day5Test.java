package aoc2020;

import aoc2020.day5.BoardingPass;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Day5Test {

    @Test
    void testReadBoardingPass_GivenExamples() {
        assertEquals(new BoardingPass(44, 5), BoardingPass.from("FBFBBFFRLR"));
        assertEquals(new BoardingPass(70, 7), BoardingPass.from("BFFFBBFRRR"));
        assertEquals(new BoardingPass(14, 7), BoardingPass.from("FFFBBBFRRR"));
        assertEquals(new BoardingPass(102, 4), BoardingPass.from("BBFFBBFRLL"));
    }

    @Test
    void testReadBoardingPass_InputValidation() {
        assertDoesNotThrow(() -> {
            BoardingPass.from("BFFFBBFRRR");
        });
        assertDoesNotThrow(() -> {
            BoardingPass.from("FFFBBBFRRR");
        });
        assertDoesNotThrow(() -> {
            BoardingPass.from("BBFFBBFRLL");
        });

        assertThrows(IllegalArgumentException.class, () -> BoardingPass.from("BFFFBBFRRa")); // wrong char in last 3
        assertThrows(IllegalArgumentException.class, () -> BoardingPass.from("FFFBBFRRL")); // too short
        assertThrows(IllegalArgumentException.class, () -> BoardingPass.from("BFFFBBFRBL")); // wrong char in last 3
        assertThrows(IllegalArgumentException.class, () -> BoardingPass.from("BFFFBBFRRLL")); // too long
        assertThrows(IllegalArgumentException.class, () -> BoardingPass.from("BFFFBBFRRLa")); // too long and wrong char
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