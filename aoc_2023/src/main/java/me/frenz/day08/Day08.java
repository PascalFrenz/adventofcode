
package me.frenz.day08;

import me.frenz.Day;
import me.frenz.Pair;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day08 extends Day<Long, Long> {

    public Day08(List<String> input) {
        super(input);
    }

    @Override
    protected Long part1() {
        return solve("AAA", parseInstructions(), parseMap());
    }

    @Override
    protected Long part2() {
        final Map<String, Pair<String, String>> map = parseMap();
        final List<String> instructions = parseInstructions();
        final List<String> starts = map.keySet().stream().filter(loc -> loc.endsWith("A")).toList();

        final long result = starts.stream()
                .map(start -> BigInteger.valueOf(solve(start, instructions, map)))
                .reduce(BigInteger.ONE, Day08::lcm)
                .longValue();

        return Math.abs(result);
    }

    private List<String> parseInstructions() {
        return Arrays.stream(input.get(0).split("")).toList();
    }

    private Map<String, Pair<String, String>> parseMap() {
        return IntStream.range(2, input.size()).mapToObj(input::get).map(line -> {
            final String source = line.split(" = ")[0];
            final String destLeft = line.split(" = ")[1].split(", ")[0].substring(1);
            final String destRight = line.split(" = ")[1].split(", ")[1].substring(0, 3);

            return Map.entry(source, Pair.of(destLeft, destRight));
        }).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private long solve(final String start, List<String> instructions, Map<String, Pair<String, String>> map) {
        long steps = 0;
        String currentPos = start;
        while (!currentPos.endsWith("Z")) {
            final String instruction = instructions.get(Math.floorMod(steps, instructions.size()));
            currentPos = "L".equals(instruction)
                    ? map.get(currentPos).left()
                    : map.get(currentPos).right();
            steps++;
        }

        return steps;
    }

    static BigInteger lcm(BigInteger number1, BigInteger number2) {
        BigInteger gcd = number1.gcd(number2);
        BigInteger absProduct = number1.multiply(number2).abs();
        return absProduct.divide(gcd);
    }

}
