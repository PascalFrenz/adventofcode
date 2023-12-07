package me.frenz.day07;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

record Hand(Map<Card, Integer> cards, int bid, List<Card> cardsInOrder) {

    static Hand from(final String handWithBid) {
        final String[] cardStrs = handWithBid.split(" ")[0].split("");
        final int bid = Integer.parseInt(handWithBid.split(" ")[1]);
        final List<Card> cardsInOrder = Arrays.stream(cardStrs).map(Card::from).toList();
        final Map<Card, Integer> cards = cardsInOrder.stream().collect(Collectors.groupingBy(Function.identity(), Collectors.reducing(0, e -> 1, Integer::sum)));
        return new Hand(cards, bid, cardsInOrder);
    }

    static Comparator<Hand> comparatorWithJoker() {
        return (hand, other) -> {
            final int handTypeComparison = HandType.applyJokerRule(hand).compareTo(HandType.applyJokerRule(other));
            if (handTypeComparison != 0) {
                return handTypeComparison;
            } else {
                int idx = 0;
                int comparison = 0;
                while (idx < hand.cardsInOrder.size() && comparison == 0) {
                    final Card card = hand.cardsInOrder.get(idx);
                    final Card otherCard = other.cardsInOrder.get(idx);
                    if (card == Card.J && otherCard != Card.J) {
                        comparison = -1;
                    } else if (card != Card.J && otherCard == Card.J) {
                        comparison = 1;
                    } else {
                        comparison = card.compareTo(otherCard);
                    }
                    idx++;
                }
                return comparison;
            }
        };
    }

    static Comparator<Hand> comparator() {
        return (hand, other) -> {
            final int handTypeComparison = HandType.from(hand).compareTo(HandType.from(other));
            if (handTypeComparison != 0) {
                return handTypeComparison;
            } else {
                int idx = 0;
                int comparison = 0;
                while (idx < hand.cardsInOrder.size() && comparison == 0) {
                    final Card card = hand.cardsInOrder.get(idx);
                    final Card otherCard = other.cardsInOrder.get(idx);
                    comparison = card.compareTo(otherCard);
                    idx++;
                }
                return comparison;
            }
        };
    }
}
