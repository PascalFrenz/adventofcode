package me.frenz.day08;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class Forest {

    private final int[][] heightMap;

    Forest(List<String> input) {
        this.heightMap = input.stream()
                .map(String::chars)
                .map(intStream -> intStream.map(Character::getNumericValue).toArray())
                .toArray(int[][]::new);
    }


    public int countVisibleTrees() {
        int visibleTrees = 0;
        for (int y = 0; y < heightMap.length; y++) {
            for (int x = 0; x < heightMap[y].length; x++) {
                visibleTrees += isVisible(x, y) ? 1 : 0;
            }
        }
        return visibleTrees;
    }

    public int getHighestScenicScore() {
        int highestScenicScore = 0;
        for (int y = 0; y < heightMap.length; y++) {
            for (int x = 0; x < heightMap[y].length; x++) {
                highestScenicScore = Math.max(highestScenicScore, getScenicScore(x, y));
            }
        }
        return highestScenicScore;
    }

    protected boolean isVisible(int x, int y) {
        if (y == 0 || y == heightMap.length - 1) {
            return true;
        }
        if (x == 0 || x == heightMap[y].length - 1) {
            return true;
        }

        final int[] top = IntStream.iterate(y - 1, it -> it >= 0, it -> it - 1).map(it -> heightMap[it][x]).toArray();
        final int[] down = IntStream.iterate(heightMap.length - 1, it -> it >= y + 1, it -> it - 1).map(it -> heightMap[it][x]).toArray();
        final int[] left = IntStream.iterate(x - 1, it -> it >= 0, it -> it - 1).map(it -> heightMap[y][it]).toArray();
        final int[] right = IntStream.iterate(heightMap[y].length - 1, it -> it >= x + 1, it -> it - 1).map(it -> heightMap[y][it]).toArray();

        return Arrays.stream(top).allMatch(height -> height < heightMap[y][x]) ||
                Arrays.stream(down).allMatch(height -> height < heightMap[y][x]) ||
                Arrays.stream(left).allMatch(height -> height < heightMap[y][x]) ||
                Arrays.stream(right).allMatch(height -> height < heightMap[y][x]);
    }

    protected int getScenicScore(int x, int y) {
        final int[] tops = IntStream
                .iterate(y - 1, it -> it >= 0, it -> it - 1)
                .map(it -> heightMap[it][x])
                .toArray();
        final int[] downs = IntStream
                .iterate(y + 1, it -> it <= heightMap.length - 1, it -> it + 1)
                .map(it -> heightMap[it][x])
                .toArray();
        final int[] lefts = IntStream
                .iterate(x - 1, it -> it >= 0, it -> it - 1)
                .map(it -> heightMap[y][it])
                .toArray();
        final int[] rights = IntStream
                .iterate(x + 1, it -> it <= heightMap[y].length - 1, it -> it + 1)
                .map(it -> heightMap[y][it])
                .toArray();
        int top = getVisibleTreeCount(y, x, tops);
        int down = getVisibleTreeCount(y, x, downs);
        int left = getVisibleTreeCount(y, x, lefts);
        int right = getVisibleTreeCount(y, x, rights);

        return top * down * left * right;
    }

    private int getVisibleTreeCount(int y, int x, int[] tops) {
        int topCount = 0;
        int i = 0;
        boolean stop = false;
        while (i < tops.length && !stop) {
            int topV = tops[i];
            stop = topV >= heightMap[y][x];
            topCount++;
            i++;
        }
        return topCount;
    }
}
