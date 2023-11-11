package me.frenz.day01;

import me.frenz.Day;
import me.frenz.Triple;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class Day01 extends Day<Triple<Integer, Integer, Integer>, Integer> {

    private static int[] left;
    private static int[] right;
    private static int[] center;

    public Day01(List<String> input) {
        super(input);
        initIterationArrays(input.stream());
    }

    private void initIterationArrays(final Stream<String> input) {
        left = input.mapToInt(Integer::valueOf).toArray();

        right = new int[left.length];
        System.arraycopy(left, 0, right, 0, left.length);

        center = new int[left.length];
        System.arraycopy(left, 0, center, 0, left.length);
    }

    @Override
    protected Triple<Integer, Integer, Integer> part1() {
        return findPair();
    }

    @Override
    protected Integer part2() {
        Triple<Integer, Integer, Integer> result = findPair();
        return result.left() * result.right() * result.center();
    }

    private static Triple<Integer, Integer, Integer> findPair() {
        Optional<Triple<Integer, Integer, Integer>> result = Optional.empty();
        for (int i = 0; i < left.length && result.isEmpty(); i++) {
            int l = left[i];
            for (int j = 0; j < right.length && result.isEmpty(); j++) {
                int r = right[j];
                result = Arrays.stream(center)
                        .filter(c -> l + r + c == 2020)
                        .mapToObj(c -> Triple.of(l, c, r))
                        .findFirst();
            }
        }
        return result.orElse(null);
    }
}
