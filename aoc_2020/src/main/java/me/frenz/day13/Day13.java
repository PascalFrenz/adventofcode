package me.frenz.day13;

import me.frenz.Day;
import me.frenz.Pair;

import java.util.*;

public class Day13 extends Day<Integer, Long> {

    public Day13(List<String> input) {
        super(input);
    }

    @Override
    protected Integer part1() {
        assert input.size() == 2;
        final int estimatedDepartureTime = Integer.parseInt(input.get(0));
        final List<Integer> busIds = Arrays.stream(input.get(1).split(","))
                .map(val -> Objects.equals(val, "x") ? Optional.<Integer>empty() : Optional.of(Integer.valueOf(val)))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();

        final Pair<Integer, Integer> minWaitTime = busIds.stream()
                .map(id -> Pair.of(id, (Math.floorDiv(estimatedDepartureTime, id) + 1) * id - estimatedDepartureTime))
                .min(Comparator.comparing(Pair::right, Integer::compare))
                .orElseThrow();

        return minWaitTime.left() * minWaitTime.right();
    }

    @Override
    protected Long part2() {
        final String[] busIds = input.get(1).split(",");
        final List<Pair<Integer, Integer>> idsWithIndex = new ArrayList<>(busIds.length);
        for (int i = 0; i < busIds.length; i++) {
            if (!Objects.equals(busIds[i], "x")) {
                idsWithIndex.add(Pair.of(i, Integer.valueOf(busIds[i])));
            }
        }

        long currentTimestamp = idsWithIndex.get(0).right();
        long addidive = currentTimestamp;
        for (int i = 0; i < idsWithIndex.size(); i++) {
            final List<Pair<Integer, Integer>> sublist = idsWithIndex.subList(0, i + 1);

            while (!allIdsMatch(sublist, currentTimestamp)) {
                currentTimestamp += addidive;
            }

            addidive = sublist.stream()
                    .map(Pair::right)
                    .mapToLong(Long::valueOf)
                    .reduce(1L, Math::multiplyExact);
        }

        return currentTimestamp;
    }

    private static boolean allIdsMatch(List<Pair<Integer, Integer>> sublist, long currentTimestamp) {
        return sublist.stream().allMatch(pair -> (currentTimestamp + pair.left()) % pair.right() == 0);
    }
}
