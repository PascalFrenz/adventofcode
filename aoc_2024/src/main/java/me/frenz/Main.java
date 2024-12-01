package me.frenz;

import com.github.freva.asciitable.AsciiTable;
import me.frenz.day01.Day01;

import java.io.*;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        int day = 0;
        days.put(++day, new Day01(loadInput(day)));
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
                return r.lines().toList();
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
