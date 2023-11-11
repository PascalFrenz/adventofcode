package me.frenz.day09;

import me.frenz.Pair;

import java.util.Arrays;

class Window {
    private final long[] src;
    private final long[] window;
    private int lastWindowIdx;

    public Window(int size, long[] src) {
        this.src = src;
        this.lastWindowIdx = size - 1;
        this.window = new long[size];
        System.arraycopy(this.src, 0, window, 0, size);
    }

    public void advance() {
        System.arraycopy(window, 1, window, 0, window.length - 1);
        window[window.length - 1] = src[++lastWindowIdx];
    }

    public boolean containsTwoNumbersThatSumTo(final long search) {
        boolean containsSum = false;
        for (int i = 0; i < window.length && !containsSum; i++) {
            long e = window[i];
            containsSum = Arrays.stream(window)
                    .filter(c -> e + c == search)
                    .mapToObj(c -> Pair.of(c, e))
                    .findFirst()
                    .isPresent();
        }
        return containsSum;
    }

    public boolean sumEquals(final long other) {
        return Arrays.stream(window).sum() == other;
    }

    public long getMinMaxSum() {
        return Arrays.stream(window).min().orElseThrow() + Arrays.stream(window).max().orElseThrow();
    }
}
