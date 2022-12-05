package me.frenz.day05;

import me.frenz.Day;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;


public class Day05 extends Day<String, String> {

    public Day05(List<String> input) {
        super(input);
    }

    @Override
    protected String part1() {
        final HashMap<Integer, Stack<Character>> stacks = parseInputStacks();
        final List<CraneMovement> craneMovements = parseCraneMovements();

        moveCratesOneByOne(stacks, craneMovements);

        return joinCrateLettersAtTop(stacks);
    }

    @Override
    protected String part2() {
        final HashMap<Integer, Stack<Character>> stacks = parseInputStacks();
        final List<CraneMovement> craneMovements = parseCraneMovements();

        moveCratesInBatches(stacks, craneMovements);

        return joinCrateLettersAtTop(stacks);
    }

    private static String joinCrateLettersAtTop(final HashMap<Integer, Stack<Character>> stacks) {
        return stacks.values().stream()
                .map(Stack::peek)
                .map(String::valueOf)
                .collect(Collectors.joining());
    }

    private static void moveCratesInBatches(final HashMap<Integer, Stack<Character>> stacks, final List<CraneMovement> craneMovements) {
        for (final CraneMovement craneMovement : craneMovements) {
            final Stack<Character> from = stacks.get(craneMovement.from());
            final Stack<Character> to = stacks.get(craneMovement.to());
            final List<Character> toMove = from.subList(from.size() - craneMovement.amount(), from.size());
            to.addAll(List.copyOf(toMove));
            toMove.clear();
        }
    }

    private static void moveCratesOneByOne(final HashMap<Integer, Stack<Character>> stacks, final List<CraneMovement> craneMovements) {
        for (final CraneMovement craneMovement : craneMovements) {
            final Stack<Character> from = stacks.get(craneMovement.from());
            final Stack<Character> to = stacks.get(craneMovement.to());
            for (int i = 0; i < craneMovement.amount(); i++) {
                to.push(from.pop());
            }
        }
    }

    protected HashMap<Integer, Stack<Character>> parseInputStacks() {
        final HashMap<Integer, Stack<Character>> stacks = new HashMap<>();
        final LinkedList<String> stackDef = input.stream().takeWhile(s -> !s.isBlank()).collect(Collectors.toCollection(LinkedList::new));

        stackDef.removeLast(); // last row contains stack definitions... we don't need those
        for (final String row : stackDef) {
            for (int i = 1, s = 1; i < row.length(); s++) {
                final char ch = row.charAt(i);
                if (!Character.isWhitespace(ch)) {
                    stacks.putIfAbsent(s, new Stack<>());
                    stacks.get(s).insertElementAt(ch, 0);
                }
                i += 4;
            }
        }
        return stacks;
    }

    protected List<CraneMovement> parseCraneMovements() {
        return input.stream()
                .dropWhile(s -> !s.isBlank())
                .skip(1)
                .map(moveStr -> {
                    final String[] split = moveStr.split("\\s");
                    return new String[]{split[1], split[3], split[5]};
                })
                .map(movements -> new CraneMovement(
                        Integer.parseInt(movements[0]),
                        Integer.parseInt(movements[1]),
                        Integer.parseInt(movements[2])
                ))
                .toList();
    }
}
