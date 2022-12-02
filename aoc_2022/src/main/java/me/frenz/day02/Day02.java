package me.frenz.day02;

import me.frenz.Day;

import java.util.List;

import static me.frenz.day02.RPSHand.PAPER;
import static me.frenz.day02.RPSHand.ROCK;
import static me.frenz.day02.RPSHand.SCISSORS;


public class Day02 extends Day<Integer, Integer> {

    public Day02(List<String> input) {
        super(input);
    }

    @Override
    protected Integer part1() {
        return this.input
                .stream()
                .map(round -> {
                    final String[] parts = round.split(" ");
                    final RPSHand opponent = RPSHand.from(parts[0]);
                    final RPSHand me = RPSHand.from(parts[1]);

                    return getRoundResult(me, opponent) + me.value;
                })
                .reduce(Integer::sum)
                .orElse(-1);
    }

    @Override
    protected Integer part2() {
        return this.input
                .stream()
                .map(round -> {
                    final String[] parts = round.split(" ");
                    final RPSHand opponent = RPSHand.from(parts[0]);
                    final RPSHand me = getRoundAnswer(opponent, parts[1]);

                    return getRoundResult(me, opponent) + me.value;
                })
                .reduce(Integer::sum)
                .orElse(-1);
    }

    private int getRoundResult(final RPSHand me, final RPSHand opponent) {
        return switch (me) {
            case ROCK -> switch (opponent) {
                case ROCK -> 3;
                case PAPER -> 0;
                case SCISSORS -> 6;
            };
            case PAPER -> switch (opponent) {
                case ROCK -> 6;
                case PAPER -> 3;
                case SCISSORS -> 0;
            };
            case SCISSORS -> switch (opponent) {
                case ROCK -> 0;
                case PAPER -> 6;
                case SCISSORS -> 3;
            };
        };
    }

    private RPSHand getRoundAnswer(final RPSHand opponent, final String neededEnd) {
        return switch (opponent) {
            case ROCK -> switch (neededEnd) {
                case "X" -> SCISSORS;
                case "Y" -> ROCK;
                case "Z" -> PAPER;
                default -> throw new IllegalArgumentException("Letter \"%S\" not supported".formatted(neededEnd));
            };
            case PAPER -> switch (neededEnd) {
                case "X" -> ROCK;
                case "Y" -> PAPER;
                case "Z" -> SCISSORS;
                default -> throw new IllegalArgumentException("Letter \"%S\" not supported".formatted(neededEnd));
            };
            case SCISSORS -> switch (neededEnd) {
                case "X" -> PAPER;
                case "Y" -> SCISSORS;
                case "Z" -> ROCK;
                default -> throw new IllegalArgumentException("Letter \"%S\" not supported".formatted(neededEnd));
            };
        };
    }
}
