package aoc2020.day5;

import aoc2020.Util;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day5 {

    public static void main(String[] args) {
        solveA();
        solveB();
    }

    private static void solveA() {
        final Optional<Stream<String>> inputA = Util.readFile(Day5.class, "input5.txt");

        inputA.orElse(Stream.empty())
                .map(BoardingPass::from)
                .map(BoardingPass::getSeatId)
                .max(Integer::compareTo)
                .ifPresent(System.out::println);
    }

    private static void solveB() {
        final Optional<Stream<String>> inputB = Util.readFile(Day5.class, "input5.txt");

        final List<Integer> seats = inputB.orElse(Stream.empty())
                .map(BoardingPass::from)
                .filter(boardingPass -> boardingPass.getRow() != 0)
                .filter(boardingPass -> boardingPass.getRow() != 127)
                .map(BoardingPass::getSeatId)
                .sorted()
                .collect(Collectors.toList());

        // As the adjacent seats are guaranteed to be in the list we can safely
        // assume that the missing seat is not the first one
        int missingSeat = seats.get(0);
        for (Integer seat : seats) {
            if (seat != missingSeat)
                break;
            missingSeat++;
        }

        System.out.println(missingSeat);
    }
}
