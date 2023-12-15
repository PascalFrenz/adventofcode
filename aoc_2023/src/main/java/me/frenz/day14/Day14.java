
package me.frenz.day14;

import me.frenz.Day;

import java.util.List;

public class Day14 extends Day<Integer, Integer> {

    public Day14(List<String> input) {
        super(input);
    }

    @Override
    protected Integer part1() {
        final Dish tiltedDish = new Dish(input).tilt(Direction.NORTH);
        return tiltedDish.calculateLoad();
    }

    @Override
    protected Integer part2() {
        final Dish dish = new Dish(input);
        dish.spinDishNCyclesEfficient(1000000000L);
        return dish.calculateLoad();
    }

}
