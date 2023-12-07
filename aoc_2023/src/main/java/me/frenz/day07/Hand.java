package me.frenz.day07;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

record Hand(Map<Card, Integer> cards, int bid, List<Card> cardsInOrder) implements Comparable<Hand> {

    static Hand from(final String handWithBid) {
        final String[] cardStrs = handWithBid.split(" ")[0].split("");
        final int bid = Integer.parseInt(handWithBid.split(" ")[1]);
        final List<Card> cardsInOrder = Arrays.stream(cardStrs).map(Card::from).toList();
        final Map<Card, Integer> cards = cardsInOrder.stream().collect(Collectors.groupingBy(Function.identity(), Collectors.reducing(0, e -> 1, Integer::sum)));
        return new Hand(cards, bid, cardsInOrder);
    }

    @Override
    public int compareTo(Hand o) {
        final int handTypeComparison = HandType.from(this).compareTo(HandType.from(o));
        if (handTypeComparison != 0) {
            return handTypeComparison;
        } else {
            int idx = 0;
            int comparison = 0;
            while (idx < this.cardsInOrder.size() && comparison == 0) {
                comparison = this.cardsInOrder.get(idx).compareTo(o.cardsInOrder.get(idx));
                idx++;
            }
            return comparison;
        }
    }
}
