
package me.frenz.day07;

import me.frenz.*;

import java.util.*;
import java.util.function.Function;

import static me.frenz.Direction.*;

public class Day07 extends Day<Integer, Integer> {

    private static final char START = 'S';
    private static final char SPLITTER = '^';
    private static final char BEAM = '|';

    private final TachyonManifold tachyonManifold;

    public Day07(List<String> input) {
        super(input);
        tachyonManifold = new TachyonManifold(input);
    }

    @Override
    protected Integer part1() {
        tachyonManifold.simulate();
        return tachyonManifold.numberOfTimesTheBeamSplit();
    }

    @Override
    protected Integer part2() {
        tachyonManifold.simulate();
        return tachyonManifold.totalTimeSplits();
    }

    class TachyonManifold {

        final TaskMap<Character> taskMap;
        final Set<Position> beamsSplitAt;
        int timeSplits;
        boolean simulated;

        TachyonManifold(List<String> input) {
            this.taskMap = TaskMap.fromString(input, Function.identity());;
            this.beamsSplitAt = new HashSet<>();
            this.timeSplits = 0;
            this.simulated = false;
        }

        void simulate() {
            if (simulated) {
                return; // No need to do all of it again
            }

            final Position start = new Position(input.getFirst().indexOf(START), 0);
            final Stack<Pair<Position, Integer>> beams = new Stack<>();
            beams.push(Pair.of(start, 1));

            while(!beams.isEmpty()) {
                final var beam = beams.removeFirst();
                final var pos = beam.left();
                final var realities = beam.right();
                final var symbol = taskMap.at(pos, '.');
                if (symbol == SPLITTER) {
                    final Position left = pos.move(LEFT);
                    final Position right = pos.move(RIGHT);
                    if (taskMap.isWithinBounds(left) || taskMap.isWithinBounds(right)) {
                        beams.stream()
                                .filter(it -> it.left().equals(left))
                                .findFirst()
                                .ifPresentOrElse(
                                        it -> {
                                            beams.remove(it);
                                            beams.push(Pair.of(left, realities + it.right()));
                                        },
                                        () -> beams.push(Pair.of(left, realities)));

                        beams.stream()
                                .filter(it -> it.left().equals(right))
                                .findFirst()
                                .ifPresentOrElse(
                                        it -> {
                                            beams.remove(it);
                                            beams.push(Pair.of(right,  realities + it.right()));
                                        },
                                        () -> beams.push(Pair.of(right, realities)));

                        beamsSplitAt.add(pos);
                    }
                } else {
                    final Position down = pos.move(DOWN);
                    if (taskMap.isWithinBounds(down)) {
                        beams.push(Pair.of(down, realities));
                        taskMap.set(pos, BEAM);
                    } else {
                        timeSplits += realities;
                    }
                }
            }
            this.simulated = true;
        }

        int numberOfTimesTheBeamSplit() {
            return beamsSplitAt.size();
        }

        int totalTimeSplits() {
            return timeSplits;
        }
    }

}
