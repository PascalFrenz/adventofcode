package me.frenz.day05;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RangeMappingTest {

    @Test
    void testRangeMapping() {
        final RangeMapping mapping = new RangeMapping();
        mapping.add(new Range(50, 98, 2));
        mapping.add(new Range(52, 50, 48));

        assertEquals(48, mapping.getMappedValue(48));
        assertEquals(52, mapping.getMappedValue(50));
        assertEquals(99, mapping.getMappedValue(97));
        assertEquals(50, mapping.getMappedValue(98));
        assertEquals(51, mapping.getMappedValue(99));
        assertEquals(100, mapping.getMappedValue(100));
    }
}
