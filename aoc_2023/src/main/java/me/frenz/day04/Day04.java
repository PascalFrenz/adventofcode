
package me.frenz.day04;

import me.frenz.Day;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

record Card(int id, List<Integer> draws, List<Integer> winningNumbers) {
}

public class Day04 extends Day<Integer, Integer> {

    public Day04(List<String> input) {
        super(input);
    }

    @Override
    protected Integer part1() {
        final AtomicInteger points = new AtomicInteger();

        forEachCard((card) -> {
            int cardValue = 0;
            boolean firstMatch = true;

            for (Integer draw : card.draws()) {
                if (card.winningNumbers().contains(draw)) {
                    cardValue = firstMatch ? 1 : cardValue * 2;
                    firstMatch = false;
                }
            }

            points.addAndGet(cardValue);
        });

        return points.get();
    }

    @Override
    protected Integer part2() {
        final Map<Integer, Integer> cards = new HashMap<>();
        forEachCard((card) -> {
            cards.putIfAbsent(card.id(), 1);

            int cardWins = (int) card.winningNumbers().stream().filter(card.draws()::contains).count();
            int instances = cards.get(card.id());
            for (int j = card.id() + 1; j <= card.id() + cardWins; j++) {
                cards.compute(j, (id, val) -> val == null ? 1 + instances : val + instances);
            }
        });
        return cards.values().stream().reduce(0, Integer::sum);
    }

    private void forEachCard(Consumer<Card> fn) {
        for (String line : input) {
            final String resultLine = line.split(":")[1].trim();
            final String[] results = resultLine.split("\\|");

            final List<Integer> myNumbers = Arrays.stream(results[0].trim().split(" ")).filter(it -> !it.isEmpty()).map(Integer::parseInt).toList();
            final List<Integer> winningNumbers = Arrays.stream(results[1].trim().split(" ")).filter(it -> !it.isEmpty()).map(Integer::parseInt).toList();
            final int cardId = Integer.parseInt(line.split(":")[0].trim().split("Card ")[1].trim());

            fn.accept(new Card(cardId, myNumbers, winningNumbers));
        }
    }

}
