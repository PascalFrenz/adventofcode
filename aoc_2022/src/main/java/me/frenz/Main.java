package me.frenz;

import com.github.freva.asciitable.AsciiTable;
import me.frenz.day01.Day01;
import me.frenz.day02.Day02;
import me.frenz.day03.Day03;
import me.frenz.day04.Day04;
import me.frenz.day05.Day05;
import me.frenz.day06.Day06;
import me.frenz.day07.Day07;
import me.frenz.day08.Day08;
import me.frenz.day09.Day09;
import me.frenz.day10.Day10;
import me.frenz.day11.Day11;
import me.frenz.day12.Day12;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UncheckedIOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

public class Main {
    public static void main(String[] args) {
        final String[] headers = {"Day", "Task A", "Task B"};
        final String[][] data = loadDays()
                .entrySet()
                .stream()
                .map((dayEntry) -> new String[]{
                        dayEntry.getKey().toString(),
                        dayEntry.getValue().part1().toString(),
                        dayEntry.getValue().part2().toString()
                })
                .toArray(String[][]::new);

        System.out.println(AsciiTable.getTable(headers, data));
    }

    private static Map<Integer, Day<?, ?>> loadDays() {
        final HashMap<Integer, Day<?, ?>> days = new HashMap<>();
        days.put(1, new Day01(loadInput(1)));
        days.put(2, new Day02(loadInput(2)));
        days.put(3, new Day03(loadInput(3)));
        days.put(4, new Day04(loadInput(4)));
        days.put(5, new Day05(loadInput(5)));
        days.put(6, new Day06(loadInput(6)));
        days.put(7, new Day07(loadInput(7)));
        days.put(8, new Day08(loadInput(8)));
        days.put(9, new Day09(loadInput(9)));
        days.put(10, new Day10(loadInput(10)));
        days.put(11, new Day11(loadInput(11)));
        days.put(12, new Day12(loadInput(12)));
        return days;
    }

    private static List<String> loadInput(int day) {
        String paddedDay = String.valueOf(day);
        if (day < 10) {
            paddedDay = "0" + day;
        }
        String fileName = "day" + paddedDay + ".txt";

        final InputStream in = ClassLoader.getSystemResourceAsStream(fileName);
        if (in != null) {
            try (BufferedReader r = new BufferedReader(new InputStreamReader(in))) {
                return r.lines().collect(toList());
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            } catch (NullPointerException e) {
                System.err.println("Could not load file" + fileName);
                return Collections.emptyList();
            }
        } else {
            System.err.println("Could not load file " + fileName);
            return Collections.emptyList();
        }
    }
}
