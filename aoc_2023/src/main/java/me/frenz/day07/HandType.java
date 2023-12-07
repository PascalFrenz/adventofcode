package me.frenz.day07;

import java.util.Map;

public enum HandType {
    HIGH_CARD,
    ONE_PAIR,
    TWO_PAIR,
    THREE_OF_A_KIND,
    FULL_HOUSE,
    FOUR_OF_A_KIND,
    FIVE_OF_A_KIND;

    static HandType from(final Hand hand) {
        final Map<Card, Integer> cards = hand.cards();
        if (cards.containsValue(5) && cards.size() == 1) {
            return FIVE_OF_A_KIND;
        }
        if (cards.containsValue(4) && cards.size() == 2) {
            return FOUR_OF_A_KIND;
        }
        if (cards.size() == 2 && cards.containsValue(3) && cards.containsValue(2)) {
            return FULL_HOUSE;
        }
        if (cards.containsValue(3) && cards.size() >= 2) {
            return THREE_OF_A_KIND;
        }
        if (cards.values().stream().filter(i -> i == 2).count() == 2) {
            return TWO_PAIR;
        }
        if (cards.containsValue(2)) {
            return ONE_PAIR;
        }
        return HIGH_CARD;
    }
}
