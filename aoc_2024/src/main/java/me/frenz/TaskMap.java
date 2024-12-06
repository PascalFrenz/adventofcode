package me.frenz;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;

public class TaskMap<T> {

    private final Map<Pair<Integer, Integer>, T> internalRepresentation = new HashMap<>();
    private final int width;
    private final int height;

    public TaskMap(Map<Pair<Integer, Integer>, T> map) {
        this.internalRepresentation.putAll(map);
        this.width = internalRepresentation.keySet().stream().map(Pair::left).max(Integer::compareTo).orElse(0);
        this.height = internalRepresentation.keySet().stream().map(Pair::right).max(Integer::compareTo).orElse(0);
    }

    public static <T> TaskMap<T> fromString(List<String> initialMap, Function<Character, T> charMapper) {
        var map = new HashMap<Pair<Integer, Integer>, T>();
        for (int y = 0; y < initialMap.size(); y++) {
            String line = initialMap.get(y);
            for (int x = 0; x < line.length(); x++) {
                map.put(new Pair<>(x, y), charMapper.apply(line.charAt(x)));
            }
        }

        return new TaskMap<>(map);
    }

    public int width() {
        return width;
    }

    public int height() {
        return height;
    }

    public String asString(BiFunction<Pair<Integer, Integer>, T, String> valueStringMapper) {
        StringBuilder sb = new StringBuilder();
        for (int y = 0; y <= height; y++) {
            for (int x = 0; x <= width; x++) {
                Pair<Integer, Integer> position = new Pair<>(x, y);
                T value = internalRepresentation.getOrDefault(position, null);
                sb.append(Optional.of(valueStringMapper.apply(position, value)).orElse(" "));
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public T at(int x, int y, T orElse) {
        return internalRepresentation.getOrDefault(new Pair<>(x, y), orElse);
    }

    public T atRelativeFrom(int x, int y, Direction d, int steps, T orElse) {
        return this.at(
                x + d.x() * steps,
                y + d.y() * steps,
                orElse
        );
    }

    public Iterator<Pair<Integer, Integer>> iterator() {
        return internalRepresentation.keySet().iterator();
    }
}
