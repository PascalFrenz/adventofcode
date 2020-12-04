package aoc2020;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Day3 {

    private static final char TREE = '#';

    public static void main(String[] args) {
        final List<char[]> forest = Util.readFile(Day3.class, "input3.txt")
                .map(stream -> stream.map(String::toCharArray))
                .map(stream -> stream.collect(Collectors.toList()))
                .orElse(new ArrayList<>());

        final int a = getNumberOfTreesHit(forest, 1, 1);
        final int b = getNumberOfTreesHit(forest, 3, 1);
        final int c = getNumberOfTreesHit(forest, 5, 1);
        final int d = getNumberOfTreesHit(forest, 7, 1);
        final int e = getNumberOfTreesHit(forest, 1, 2);

        System.out.println("We hit a tree " + a + " times!");
        System.out.println("We hit a tree " + b + " times!");
        System.out.println("We hit a tree " + c + " times!");
        System.out.println("We hit a tree " + d + " times!");
        System.out.println("We hit a tree " + e + " times!");

        System.out.println("That multiplies together to " + ((long) a * b * c * d * e));
    }

    private static int getNumberOfTreesHit(final List<char[]> forest, final int stepsToRight, final int stepsDown) {
        int treeHit = 0;
        int position = 0;

        for (int i = 0; i < forest.size(); i++) {
            if ((i % stepsDown) == 0) {
                char[] level = forest.get(i);
                treeHit += level[position] == TREE ? 1 : 0;
                position = (position + stepsToRight) % level.length;
            }
        }

        return treeHit;
    }
}
