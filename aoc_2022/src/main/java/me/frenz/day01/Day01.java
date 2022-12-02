package me.frenz.day01;

import me.frenz.Day;

import java.util.Comparator;
import java.util.List;
import java.util.Stack;

public class Day01 extends Day<Integer, Integer> {

    public Day01(List<String> input) {
        super(input);
    }

    @Override
    protected Integer part1() {
        return getCaloriesPerElve()
                .stream()
                .max(Integer::compareTo)
                .orElse(0);
    }

    @Override
    protected Integer part2() {
        return getCaloriesPerElve()
                .stream()
                .sorted(Comparator.reverseOrder())
                .limit(3)
                .reduce(Integer::sum)
                .orElse(0);
    }

    private Stack<Integer> getCaloriesPerElve() {
        return this.input.stream().reduce(new Stack<>(), Day01::createSumPerBag, Day01::mergeBagSums);
    }

    private static Stack<Integer> createSumPerBag(Stack<Integer> sums, String s) {
        // add "new bag" if either the stack is empty or a new line is encountered
        if (sums.isEmpty() || s.isEmpty()) {
            sums.push(0);
        }

        // add current amount to last bag on stack
        if (!s.isEmpty()) {
            final int sum = sums.pop() + Integer.parseInt(s);
            sums.push(sum);
        }

        return sums;
    }

    private static Stack<Integer> mergeBagSums(Stack<Integer> left, Stack<Integer> right) {
        left.addAll(right);
        return left;
    }
}
