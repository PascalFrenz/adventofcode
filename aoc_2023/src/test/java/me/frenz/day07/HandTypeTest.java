package me.frenz.day07;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HandTypeTest {

    @Test
    void testDetermineHandType() {
        assertEquals(HandType.ONE_PAIR, HandType.from(Hand.from("32T3K 765")));
        assertEquals(HandType.THREE_OF_A_KIND, HandType.from(Hand.from("T55J5 684")));
        assertEquals(HandType.TWO_PAIR, HandType.from(Hand.from("KK677 28")));
        assertEquals(HandType.TWO_PAIR, HandType.from(Hand.from("KTJJT 220")));
        assertEquals(HandType.THREE_OF_A_KIND, HandType.from(Hand.from("QQQJA 483")));
    }

    @Test
    void testDetermineHandTypeWithJoker() {
        assertEquals(HandType.ONE_PAIR, HandType.applyJokerRule(Hand.from("32T3K 765")));
        assertEquals(HandType.FOUR_OF_A_KIND, HandType.applyJokerRule(Hand.from("T55J5 684")));
        assertEquals(HandType.TWO_PAIR, HandType.applyJokerRule(Hand.from("KK677 28")));
        assertEquals(HandType.FOUR_OF_A_KIND, HandType.applyJokerRule(Hand.from("KTJJT 220")));
        assertEquals(HandType.FOUR_OF_A_KIND, HandType.applyJokerRule(Hand.from("QQQJA 483")));
    }
}
