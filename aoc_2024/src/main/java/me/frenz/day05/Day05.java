package me.frenz.day05;

import me.frenz.Day;

import java.util.*;

public class Day05 extends Day<Long, Long> {

    public Day05(List<String> input) {
        super(input);
    }

    @Override
    protected Long part1() {
        // contains the number as key with all numbers that must occur after it as value
        Map<Integer, Set<Integer>> orderingRules = parseRules();

        // contains the printing orders as maps with the number as key and the position as value
        List<Map<Integer, Integer>> printingOrders = parsePrintingOrders();

        // contains all valid printing orders
        // the printing order is valid if all numbers that must occur after a number are actually after it
        List<Map<Integer, Integer>> validPrintingOrders = findValidOrderings(printingOrders, orderingRules);

        // now, get the element in the middle of the valid printing orders and sum up the values
        return validPrintingOrders.stream()
                .map(Day05::findElementInMiddle)
                .mapToLong(Map.Entry::getKey)
                .sum();
    }

    @Override
    protected Long part2() {
        // contains the number as key with all numbers that must occur after it as value
        Map<Integer, Set<Integer>> orderingRules = parseRules();

        // contains the printing orders as maps with the number as key and the position as value
        List<Map<Integer, Integer>> printingOrders = parsePrintingOrders();

        // contains all valid printing orders
        // the printing order is valid if all numbers that must occur after a number are actually after it
        List<Map<Integer, Integer>> validPrintingOrders = findValidOrderings(printingOrders, orderingRules);

        List<Map<Integer, Integer>> invalidPrintingOrders = new ArrayList<>(printingOrders);
        invalidPrintingOrders.removeAll(validPrintingOrders);

        // now, reorder the invalid printing orders so that they become valid
        List<Map<Integer, Integer>> correctedOrders = new ArrayList<>();
        for (Map<Integer, Integer> order : invalidPrintingOrders) {
            correctedOrders.add(reorder(order, orderingRules));
        }

        // find the middle element of each corrected order and sum them up
        return correctedOrders.stream()
                .map(Day05::findElementInMiddle)
                .mapToLong(Map.Entry::getKey)
                .sum();
    }

    private Map<Integer, Integer> reorder(Map<Integer, Integer> order, Map<Integer, Set<Integer>> rules) {
        List<Integer> pages = new ArrayList<>(order.keySet());
        pages.sort((a, b) -> {
            if (rules.getOrDefault(a, Collections.emptySet()).contains(b)) {
                return 1;
            } else if (rules.getOrDefault(b, Collections.emptySet()).contains(a)) {
                return -1;
            } else {
                return 0;
            }
        });
        Map<Integer, Integer> reordered = new HashMap<>();
        for (int i = 0; i < pages.size(); i++) {
            reordered.put(pages.get(i), i);
        }
        return reordered;
    }

    private static Map.Entry<Integer, Integer> findElementInMiddle(Map<Integer, Integer> map) {
        var maxEntry = map.entrySet().stream().max(Comparator.comparingInt(Map.Entry::getValue));
        var minEntry = map.entrySet().stream().min(Comparator.comparingInt(Map.Entry::getValue));
        int middleIdx = (maxEntry.orElseThrow().getValue() + minEntry.orElseThrow().getValue()) / 2;
        return map.entrySet().stream()
                .filter(entry -> entry.getValue() == middleIdx)
                .findAny()
                .orElseThrow();
    }

    private static List<Map<Integer, Integer>> findValidOrderings(List<Map<Integer, Integer>> printingOrders, Map<Integer, Set<Integer>> orderingRules) {
        List<Map<Integer, Integer>> validPrintingOrders = new ArrayList<>();
        for (Map<Integer, Integer> printingOrder : printingOrders) {
            boolean valid = true;
            for (Map.Entry<Integer, Integer> entry : printingOrder.entrySet()) {
                Set<Integer> numbersThatMustOccurAfter = orderingRules.get(entry.getKey());
                if (numbersThatMustOccurAfter != null) {
                    for (Integer number : numbersThatMustOccurAfter) {
                        if (printingOrder.getOrDefault(number, Integer.MAX_VALUE) < entry.getValue()) {
                            valid = false;
                            break;
                        }
                    }
                }
            }
            if (valid) {
                validPrintingOrders.add(printingOrder);
            }
        }
        return validPrintingOrders;
    }

    private HashMap<Integer, Set<Integer>> parseRules() {
        return input.stream().takeWhile(s -> !s.isEmpty())
                .map(s -> {
                    String[] split = s.split("\\|");
                    Set<Integer> set = HashSet.newHashSet(1);
                    set.add(Integer.parseInt(split[1]));
                    return Map.entry(Integer.parseInt(split[0]), set);
                })
                .reduce(new HashMap<>(), (map, entry) -> {
                    Optional.ofNullable(map.putIfAbsent(entry.getKey(), entry.getValue()))
                            .ifPresent(integers -> integers.addAll(entry.getValue()));
                    return map;
                }, (map1, map2) -> {
                    map1.putAll(map2);
                    return map1;
                });
    }

    private List<Map<Integer, Integer>> parsePrintingOrders() {
        return input.stream().dropWhile(s -> !s.isEmpty()).skip(1)
                .map(s -> {
                    String[] split = s.split(",");
                    Map<Integer, Integer> map = new HashMap<>();
                    for (int i = 0; i < split.length; i++) {
                        map.put(Integer.parseInt(split[i]), i);
                    }
                    return map;
                })
                .toList();
    }
}
