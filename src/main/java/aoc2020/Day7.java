package aoc2020;

import lombok.Value;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day7 {

    public static final String SHINY_GOLD = "shiny gold";

    public static void main(String[] args) {
        final Stream<String> input = Util.readFile(Day5.class, "input7.txt").orElse(Stream.empty());
        final Map<String, Bag> bags = getBagMap(input);

        System.out.println("Number of paths to the shiny gold bag: " + findBags(SHINY_GOLD, bags, new HashSet<>()).size());
        System.out.println("Number of inner bags: " + countInnerBags(SHINY_GOLD, bags));
    }

    public static Map<String, Bag> getBagMap(Stream<String> input) {
        return input.map(Bag::from).collect(Collectors.toMap(Bag::getName, Function.identity()));
    }

    public static Set<String> findBags(final String search, final Map<String, Bag> bags, final Set<String> result) {
        final Set<String> newBags = bags.values().stream()
                .map(bag -> bag.containsBag(search) ? bag.getName() : null)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        for (String newBag : newBags) {
            result.addAll(findBags(newBag, bags, result));
        }

        result.addAll(newBags);

        return result;
    }

    public static long countInnerBags(final String bag, final Map<String, Bag> bags) {
        final Bag current = bags.get(bag);
        return current.getInnerBags().stream()
                .map(bags::get)
                .mapToLong(b -> current.getCountFor(b.name) + current.getCountFor(b.name) * countInnerBags(b.name, bags))
                .sum();
    }

    @Value
    static class Bag {

        String name;
        Map<String, Long> content;

        public Bag(String name, Map<String, Long> content) {
            this.name = name;
            this.content = content;
        }

        public static Bag from(String s) {
            final String[] sanitized = s.replace(" bags contain", ",")
                    .replace(" bags", "")
                    .replace(" bag", "")
                    .replace(".", "")
                    .split(",");

            final Iterator<String> iter = Arrays.stream(sanitized).iterator();
            final String name = iter.next();

            final Map<String, Long> content = new HashMap<>();

            iter.forEachRemaining(elem -> {
                final String work = elem.trim();
                try {
                    Long count = Long.valueOf(work.substring(0, 1));
                    final String contentBagName = work.substring(1).trim();
                    content.put(contentBagName, count);
                } catch (NumberFormatException ignored) { }
            });

            return new Bag(name, content);
        }

        public boolean containsBag(final String name) {
            return content.containsKey(name);
        }

        public long getCountFor(final String name) {
            return content.getOrDefault(name, 0L);
        }

        public long getInnerBagCount() {
            return content.values().stream().reduce(Long::sum).orElse(0L);
        }

        public Set<String> getInnerBags() {
            return content.keySet();
        }
    }
}
