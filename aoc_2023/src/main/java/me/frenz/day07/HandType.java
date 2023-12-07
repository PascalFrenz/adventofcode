package me.frenz.day07;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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

    static HandType applyJokerRule(final Hand hand) {
        final HandType noJoker = HandType.from(hand);
        final Map<Card, Integer> cards = hand.cards();
        if (!cards.containsKey(Card.J)) {
            return noJoker;
        }

        final Map<Integer, Set<Card>> transposedCards = new HashMap<>();
        for (Map.Entry<Card, Integer> entry : cards.entrySet()) {
            if (entry.getKey() != Card.J) {
                transposedCards.putIfAbsent(entry.getValue(), new HashSet<>());
                transposedCards.get(entry.getValue()).add(entry.getKey());
            }
        }

        final int sameCards = transposedCards.keySet().stream()
                .max(Integer::compareTo)
                .map(it -> it + cards.get(Card.J))
                .orElse(5); // transposed cards is empty, therefore only joker cards were received
        if (sameCards == 5) {
            return FIVE_OF_A_KIND;
        } else if (sameCards == 4) {
            return FOUR_OF_A_KIND;
        } else if (sameCards == 3) {
            if (cards.values().stream().filter(i -> i == 2).count() == 2) {
                return FULL_HOUSE;
            } else {
                return THREE_OF_A_KIND;
            }
        } else if (sameCards == 2) {
            return ONE_PAIR;
        } else {
            return HIGH_CARD;
        }
    }
}
