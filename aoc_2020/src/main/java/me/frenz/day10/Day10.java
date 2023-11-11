package me.frenz.day10;

import me.frenz.Day;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Day10 extends Day<Long, BigInteger> {

    private final int[] fullScenario;

    public Day10(List<String> input) {
        super(input);
        final int[] sortedAdapters = input.stream().mapToInt(Integer::valueOf).sorted().toArray();
        fullScenario = initializeSzenario(sortedAdapters);
    }

    private int[] initializeSzenario(int[] sortedAdapters) {
        final int[] fullScenario;
        fullScenario = new int[sortedAdapters.length + 2];
        Arrays.fill(fullScenario, 0);
        System.arraycopy(sortedAdapters, 0, fullScenario, 1, sortedAdapters.length);
        fullScenario[fullScenario.length - 1] = fullScenario[fullScenario.length - 2] + 3;
        return fullScenario;
    }

    @Override
    protected Long part1() {
        final Map<Integer, Integer> differences = getJoltageDifferences(fullScenario);
        return differences.get(1).longValue() * differences.get(3).longValue();
    }

    @Override
    protected BigInteger part2() {
        final Map<Integer, BigInteger> routes = new HashMap<>();
        routes.put(0, BigInteger.ONE);
        for (int i = 1; i < fullScenario.length; i++) {
            final int adapterValue = fullScenario[i];

            final BigInteger nextElem = routes.getOrDefault(adapterValue - 1, BigInteger.ZERO)
                    .add(routes.getOrDefault(adapterValue - 2, BigInteger.ZERO))
                    .add(routes.getOrDefault(adapterValue - 3, BigInteger.ZERO));
            routes.put(adapterValue, nextElem);
        }
        return routes.get(Arrays.stream(fullScenario).max().orElse(-1) - 3);
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
}
