package me.frenz.day02;

import me.frenz.Day;
import me.frenz.Triple;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day02 extends Day<Integer, Integer> {

    public Day02(List<String> input) {
        super(input);
    }

    @Override
    protected Integer part1() {
        final Map<Integer, List<Triple<Integer, Integer, Integer>>> games = parseGames();

        return games.entrySet().stream()
                .filter(Day02::isValidGame)
                .map(Map.Entry::getKey)
                .reduce(0, Integer::sum);
    }

    @Override
    protected Integer part2() {
        final Map<Integer, List<Triple<Integer, Integer, Integer>>> games = parseGames();
        return games.values().stream().mapToInt(draws -> {
            int red = 0, green = 0, blue = 0;
            for (Triple<Integer, Integer, Integer> draw : draws) {
                red = Math.max(red, draw.left());
                green = Math.max(green, draw.center());
                blue = Math.max(blue, draw.right());
            }
            return red * green * blue;
        }).reduce(0, Integer::sum);
    }

    private static boolean isValidGame(Map.Entry<Integer, List<Triple<Integer, Integer, Integer>>> entry) {
        return entry.getValue()
                .stream()
                .allMatch(draw -> draw.left() <= 12 && draw.center() <= 13 && draw.right() <= 14);
    }

    private Map<Integer, List<Triple<Integer, Integer, Integer>>> parseGames() {
        final Map<Integer, List<Triple<Integer, Integer, Integer>>> games = new HashMap<>();
        for (String gameRecord : input) {
            final int gameId = Integer.parseInt(gameRecord.split(":")[0].split("Game ")[1]);
            final String[] draws = gameRecord.split(":")[1].trim().split(";");
            games.put(gameId, new ArrayList<>());
            for (String draw : draws) {
                int red = 0, green = 0, blue = 0;
                for (String singleDraw : draw.trim().split(",")) {
                    final String[] s = singleDraw.trim().split(" ");
                    final int amount = Integer.parseInt(s[0]);
                    final String color = s[1];
                    switch (color) {
                        case "red":
                            red += amount;
                            break;
                        case "green":
                            green += amount;
                            break;
                        case "blue":
                            blue += amount;
                            break;
                        case null, default:
                            throw new IllegalArgumentException("Unknown color: " + color);
                    }
                }

                games.get(gameId).add(Triple.of(red, green, blue));
            }
        }
        return games;
    }
}
