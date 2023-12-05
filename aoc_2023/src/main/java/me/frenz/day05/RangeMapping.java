package me.frenz.day05;

import me.frenz.Pair;

import java.util.HashMap;
import java.util.Map;

class RangeMapping {
    private final HashMap<Pair<Long, Long>, Range> ranges = new HashMap<>(1);

    long getMappedValue(long source) {
        for (Map.Entry<Pair<Long, Long>, Range> entry : ranges.entrySet()) {
            if (source >= entry.getKey().left() && source < entry.getKey().right()) {
                return entry.getValue().getDestValue(source);
            }
        }
        return source;
    }

    void add(final Range r) {
        ranges.put(Pair.of(r.sourceRangeStart(), r.sourceRangeStart() + r.length()), r);
    }
}
