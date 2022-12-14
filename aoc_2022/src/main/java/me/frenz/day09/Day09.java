package me.frenz.day09;

import me.frenz.Day;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day09 extends Day<Integer, Integer> {

    public Day09(List<String> input) {
        super(input);
    }

    @Override
    protected Integer part1() {
        final List<Move> moves = input.stream().map(Move::parse).collect(Collectors.toList());
        return moveRope(moves, initRope(2));
    }

    private int moveRope(List<Move> moves, Coordinate[] rope) {
        final HashSet<Coordinate> visitedPositions = new HashSet<>(List.of(new Coordinate(0, 0)));
        for (Move move : moves) {
            for (int i = 0; i < move.steps(); i++) {
                rope[0] = rope[0].moveOneStep(move);
                for (int j = 1; j < rope.length; j++) {
                    rope[j] = update(rope[j - 1], rope[j]);
                }
                visitedPositions.add(rope[rope.length - 1]);
            }
        }

        return visitedPositions.size();
    }

    private static Coordinate[] initRope(int size) {
        return IntStream.range(0, size).mapToObj(it -> new Coordinate(0, 0)).toArray(Coordinate[]::new);
    }

    private Coordinate update(Coordinate head, Coordinate tail) {
        if (tail.isTouching(head)) {
            return tail;
        } else if (tail.y() == head.y()) {
            return tail.moveOneStep(new Move(tail.x() < head.x() ? 1 : -1, 0, 0));
        } else if (tail.x() == head.x()) {
            return tail.moveOneStep(new Move(0, tail.y() < head.y() ? 1 : -1, 0));
        } else if (tail.y() < head.y()) {
            return tail.moveOneStep(new Move(tail.x() < head.x() ? 1 : -1, 1, 0));
        } else {
            return tail.moveOneStep(new Move(tail.x() < head.x() ? 1 : -1, -1, 0));
        }
    }

    @Override
    protected Integer part2() {
        final List<Move> moves = input.stream().map(Move::parse).collect(Collectors.toList());
        return moveRope(moves, initRope(10));
    }
}
