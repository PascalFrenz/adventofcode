package me.frenz.day05;

import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RangeTest {

    @Test
    void testSmallRangeMapping() {
        final Range range = new Range(50, 98, 2);
        assertEquals(50, range.getDestValue(98));
        assertEquals(51, range.getDestValue(99));
        assertEquals(100, range.getDestValue(100));
        assertEquals(49, range.getDestValue(49));
    }

    @Test
    void testLargeRangeMapping() {
        final Range range = new Range(52, 50, 48);
        assertEquals(49, range.getDestValue(49));
        assertEquals(52, range.getDestValue(50));
        assertEquals(98, range.getDestValue(96));
    }

    @Test
    void testToEntries() {
        final Range range = new Range(50, 98, 2);
        final Map<Long, Long> map = range.toEntries().stream().collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        assertEquals(50, map.get(98L));
        assertEquals(51, map.get(99L));
        assertEquals(2, map.size());
    }
}
