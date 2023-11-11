package me.frenz.day07;

import java.util.*;

record Bag(String name, Map<String, Long> content) {

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
            } catch (NumberFormatException ignored) {
            }
        });

        return new Bag(name, content);
    }

    public boolean containsBag(final String name) {
        return content.containsKey(name);
    }

    public long getCountFor(final String name) {
        return content.getOrDefault(name, 0L);
    }

    public Set<String> getInnerBags() {
        return content.keySet();
    }
}
