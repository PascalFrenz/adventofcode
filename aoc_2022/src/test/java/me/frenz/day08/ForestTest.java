package me.frenz.day08;

import me.frenz.Pair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ForestTest {

    private Forest forest;

    @BeforeEach
    void setUp() {
        String EXAMPLE_INPUT = """
                30373
                25512
                65332
                33549
                35390
                """;
        forest = new Forest(EXAMPLE_INPUT.lines().collect(Collectors.toList()));
    }

    @Test
    void givenPositionOnEdge_shouldBeVisible() {
        IntStream.rangeClosed(0, 4).forEach(p -> {
            assertTrue(forest.isVisible(p, 0));
            assertTrue(forest.isVisible(0, p));
        });
    }

    @Test
    void givenVisiblePositionNotOnEdge_shouldBeVisible() {
        List.of(
                new Pair<>(2, 1),
                new Pair<>(1, 1),
                new Pair<>(1, 2),
                new Pair<>(2, 3)
        ).forEach(p -> assertTrue(
                forest.isVisible(p.left(), p.right()),
                String.format("expected %s to be visible, but was not", p))
        );
    }

    @Test
    void givenSomePosition_shouldCalculateCorrectScenicScore() {
        assertEquals(4, forest.getScenicScore(2, 1));
        assertEquals(8, forest.getScenicScore(2, 3));
    }
}
