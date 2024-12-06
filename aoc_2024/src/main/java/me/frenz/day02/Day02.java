package me.frenz.day02;

import me.frenz.Day;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day02 extends Day<Integer, Integer> {

    public Day02(List<String> input) {
        super(input);
    }

    @Override
    protected Integer part1() {
        List<List<Integer>> safeLists = input.stream()
                .map(s -> Arrays.stream(s.split("\\s+")).mapToInt(Integer::parseInt).boxed().toList())
                .filter(this::isSafe)
                .toList();
        return safeLists.size();
    }

    @Override
    protected Integer part2() {
        List<List<Integer>> safeLists = new ArrayList<>();
        for (String s : input) {
            List<Integer> ints = Arrays.stream(s.split("\\s+")).mapToInt(Integer::parseInt).boxed().toList();

            if (isSafe(ints)) {
                safeLists.add(ints);
            } else {
                for (int i = 0; i < ints.size(); i++) {
                    List<Integer> modifiedList = new ArrayList<>(ints);
                    modifiedList.remove(i);
                    if (isSafe(modifiedList)) {
                        safeLists.add(ints);
                        break;
                    }
                }
            }
        }
        return safeLists.size();
    }

    private boolean isSafe(List<Integer> ints) {
        boolean ascending = true;
        boolean descending = true;
        boolean safe = true;

        for (int i = 0; i < ints.size() - 1; i++) {
            int diff = Math.abs(ints.get(i) - ints.get(i + 1));
            safe &= diff >= 1 && diff <= 3;
            ascending &= ints.get(i) < ints.get(i + 1);
            descending &= ints.get(i) > ints.get(i + 1);
        }

        return safe && (ascending || descending);
    }
}
