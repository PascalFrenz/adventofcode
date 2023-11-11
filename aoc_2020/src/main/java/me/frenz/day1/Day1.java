package me.frenz.day1;

import me.frenz.Util;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Stream;

public class Day1 {

    private static int[] left;
    private static int[] right;
    private static int[] center;


    public static void main(String[] args) {
        Optional<Stream<String>> input = Util.readFile(Day1.class, "input1.txt");

        if (input.isPresent()) {

            initIterationArrays(input.get());

            final Util.Triple<Integer, Integer, Integer> result = findPair();

            System.out.printf("Ergebnistupel: %s\n", result.toString());
            System.out.printf("Multipliziert: %d\n", result.getLeft() * result.getRight() * result.getCenter());
        }
    }

    private static Util.Triple<Integer, Integer, Integer> findPair() {
        Optional<Util.Triple<Integer, Integer, Integer>> result = Optional.empty();
        for (int i = 0; i < left.length && result.isEmpty(); i++) {
            int l = left[i];
            for (int j = 0; j < right.length && result.isEmpty(); j++) {
                int r = right[j];
                result = Arrays.stream(center)
                        .filter(c -> l + r + c == 2020)
                        .mapToObj(c -> Util.Triple.of(l, c, r))
                        .findFirst();
            }
        }
        return result.orElse(null);
    }

    private static void initIterationArrays(final Stream<String> input) {
        left = input.mapToInt(Integer::valueOf).toArray();

        right = new int[left.length];
        System.arraycopy(left, 0, right, 0, left.length);

        center = new int[left.length];
        System.arraycopy(left, 0, center, 0, left.length);
    }
}
