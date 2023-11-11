package me.frenz.day10;

import me.frenz.Util;

import java.math.BigInteger;
import java.util.*;
import java.util.stream.Stream;


public class Day10 {

    public static void main(String[] args) {
        final int[] sortedAdapters = Util.readFile(Day10.class, "day10.txt")
                .orElse(Stream.empty())
                .mapToInt(Integer::valueOf)
                .sorted()
                .toArray();
        final int[] fullScenario = new int[sortedAdapters.length + 2];
        Arrays.fill(fullScenario, 0);
        System.arraycopy(sortedAdapters, 0, fullScenario, 1, sortedAdapters.length);
        fullScenario[fullScenario.length - 1] = fullScenario[fullScenario.length - 2] + 3;
        final Map<Integer, Integer> differences = getJoltageDifferences(fullScenario);
        System.out.println(differences);
        System.out.println(differences.get(1) * differences.get(3));

        System.out.printf("Part two: %s", part2(fullScenario).toString());
    }

    private static Map<Integer, Integer> getJoltageDifferences(final int[] sortedAdapters) {
        final Map<Integer, Integer> differences = new HashMap<>();

        for (int i = 0; i < sortedAdapters.length - 1; i++) {
            int curr = sortedAdapters[i];
            int next = sortedAdapters[i + 1];
            int diff = next - curr;
            differences.compute(diff, (key, val) -> val != null ? val + 1 : 1);
        }
        return differences;
    }

    private static BigInteger part2(final int[] sortedAdapters) {
        final Map<Integer, BigInteger> routes = new HashMap<>();
        routes.put(0, BigInteger.ONE);
        for (int i = 1; i < sortedAdapters.length; i++) {
            final int adapterValue = sortedAdapters[i];

            final BigInteger nextElem = routes.getOrDefault(adapterValue - 1, BigInteger.ZERO)
                    .add(routes.getOrDefault(adapterValue - 2, BigInteger.ZERO))
                    .add(routes.getOrDefault(adapterValue - 3, BigInteger.ZERO));
            routes.put(adapterValue, nextElem);
        }
        return routes.get(Arrays.stream(sortedAdapters).max().orElse(-1) - 3);
    }
}
