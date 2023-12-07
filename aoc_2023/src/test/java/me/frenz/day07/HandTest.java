package me.frenz.day07;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class HandTest {

    @Test
    void testParseHand() {
        final Hand expected = new Hand(
                Map.of(Card.Q, 3, Card.J, 1, Card.A, 1),
                483,
                List.of(Card.Q, Card.Q, Card.Q, Card.J, Card.A)
        );
        assertEquals(expected, Hand.from("QQQJA 483"));
    }

    @Test
    void testCompareHands() {
        assertTrue(Hand.comparator().compare(Hand.from("KK677 28"), Hand.from("KTJJT 220")) > 0);
        assertTrue(Hand.comparator().compare(Hand.from("QQQJA 483"), Hand.from("T55J5 684")) > 0);
        assertEquals(0, Hand.comparator().compare(Hand.from("QQQQQ 483"), Hand.from("QQQQQ 684")));
    }

    @Test
    void testCompareHandsWithJoker() {
        assertTrue(Hand.comparatorWithJoker().compare(Hand.from("KTJJT 220"), Hand.from("QQQJA 483")) > 0);
        assertTrue(Hand.comparatorWithJoker().compare(Hand.from("QQQJA 483"), Hand.from("T55J5 684")) > 0);
    }
}
