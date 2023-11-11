package me.frenz.day05;

import me.frenz.Day;


import java.util.List;

public class Day05 extends Day<Integer, Integer> {

    public Day05(List<String> input) {
        super(input);
    }

    @Override
    protected Integer part1() {
        return input.stream()
                .map(BoardingPass::from)
                .map(BoardingPass::getSeatId)
                .max(Integer::compareTo)
                .orElse(-1);
    }

    @Override
    protected Integer part2() {
        final List<Integer> seats = input.stream()
                .map(BoardingPass::from)
                .filter(boardingPass -> boardingPass.getRow() != 0)
                .filter(boardingPass -> boardingPass.getRow() != 127)
                .map(BoardingPass::getSeatId)
                .sorted()
                .toList();

        // As the adjacent seats are guaranteed to be in the list we can safely
        // assume that the missing seat is not the first one
        int missingSeat = seats.get(0);
        for (Integer seat : seats) {
            if (seat != missingSeat)
                break;
            missingSeat++;
        }

        return missingSeat;
    }

}
