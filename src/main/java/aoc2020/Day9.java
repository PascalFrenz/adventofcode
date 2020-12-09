package aoc2020;

import java.util.Arrays;
import java.util.stream.Stream;

public class Day9 {


    public static final int WINDOW_SIZE = 25;

    public static void main(String[] args) {
        final long[] numbers = Util.readFile(Day8.class, "input9.txt")
                .orElse(Stream.empty())
                .mapToLong(Long::valueOf)
                .toArray();

        assert numbers.length > WINDOW_SIZE;

        System.out.println("First number that is not a sum: " + calculateA(numbers));
        System.out.println("Sum of smallest and largest number: " + calculateB(numbers, calculateA(numbers)));
    }

    private static long calculateA(long[] xmas) {
        int idx = WINDOW_SIZE;
        final Window window = new Window(WINDOW_SIZE, xmas);
        while (window.containsTwoNumbersThatSumTo(xmas[idx])) {
            window.advance();
            idx++;
        }

        return xmas[idx];
    }

    private static long calculateB(long[] xmas, long searched) {
        int windowSize = 2;
        Window window = new Window(windowSize, xmas);

        boolean resultFound = false;
        while (!resultFound) {
            long lastWindowIdx = windowSize - 1;
            while (!window.sumEquals(searched) && ++lastWindowIdx < xmas.length) {
                window.advance();
            }

            resultFound = window.sumEquals(searched);
            window = resultFound ? window : new Window(++windowSize, xmas);
        }

        return window.getMinMaxSum();
    }

    private static class Window {
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
                        .mapToObj(c -> Util.Pair.of(c, e))
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
}


