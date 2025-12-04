
package me.frenz.day04;

import me.frenz.Day;
import me.frenz.Pair;
import me.frenz.Position;
import me.frenz.TaskMap;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.function.Function;

public class Day04 extends Day<Integer, Integer> {

    public Day04(List<String> input) {
        super(input);
    }

    @Override
    protected Integer part1() {
        final var taskMap = TaskMap.fromString(input, Function.identity());
        return new ForkLift().countAccessible(taskMap);
    }

    @Override
    protected Integer part2() {
        final var taskMap = TaskMap.fromString(input, Function.identity());
        final var forklift = new ForkLift();
        var result = 0;
        while (forklift.countAccessible(taskMap) != 0) {
            result += forklift.accessAndRemove(taskMap);
        }
        return result;
    }

    static class ForkLift {

        int countAccessible(TaskMap<Character> map) {
            AtomicInteger result = new AtomicInteger();
            access(map, _ -> result.getAndIncrement());
            return result.get();
        }

        int accessAndRemove(TaskMap<Character> map) {
            AtomicInteger result = new AtomicInteger();
            access(map, it -> {
                result.getAndIncrement();
                map.set(it.left(), '.');
            });
            return result.get();
        }

        private void access(TaskMap<Character> map, Consumer<Pair<Position, Character>> action) {
            for (Pair<Position, Character> p : map) {
                if (p.right() != '@') {
                    continue;
                }
                final var surroundings = map.checkSurroundings(p.left(), '.');
                final var adjacentRollCount = surroundings.stream()
                        .filter(it -> '@' == it)
                        .count();
                if (adjacentRollCount < 4) {
                    action.accept(p);
                }
            }
        }
    }

}
