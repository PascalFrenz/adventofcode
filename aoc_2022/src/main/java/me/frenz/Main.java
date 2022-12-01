package me.frenz;

import com.github.freva.asciitable.AsciiTable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UncheckedIOException;
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
        return days;
    }

    private static List<String> loadInput(int day) {
        String paddedDay = String.valueOf(day);
        if (day < 10) {
            paddedDay = "0" + day;
        }
        String fileName = "day" + paddedDay + ".txt";

        try (BufferedReader r = new BufferedReader(new InputStreamReader(ClassLoader.getSystemResourceAsStream(fileName)))) {
            return r.lines().collect(toList());
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
