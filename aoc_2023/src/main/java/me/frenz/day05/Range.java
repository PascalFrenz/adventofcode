package me.frenz.day05;

import java.util.List;
import java.util.Map;
import java.util.stream.LongStream;

record Range(long destRangeStart, long sourceRangeStart, long length) {
    long getDestValue(long value) {
        if (value >= sourceRangeStart && value < sourceRangeStart + length) {
            return value - sourceRangeStart + destRangeStart;
        }

        return value;
    }

    List<Map.Entry<Long, Long>> toEntries() {
        return LongStream.range(sourceRangeStart, sourceRangeStart + length)
                .mapToObj(i -> Map.entry(i, this.getDestValue(i)))
                .toList();
    }
}

