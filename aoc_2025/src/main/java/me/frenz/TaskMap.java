package me.frenz;

import lombok.NonNull;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

public class TaskMap<T> implements Iterable<Pair<Position, T>> {

    private final Map<Position, T> internalRepresentation = new HashMap<>();
    private final int width;
    private final int height;

    public TaskMap(Map<Position, T> map) {
        this.internalRepresentation.putAll(map);
        this.width = internalRepresentation.keySet().stream().map(Position::x).max(Integer::compareTo).orElse(0);
        this.height = internalRepresentation.keySet().stream().map(Position::y).max(Integer::compareTo).orElse(0);
    }

    public static <T> TaskMap<T> fromString(List<String> initialMap, Function<Character, T> charMapper) {
        var map = new HashMap<Position, T>();
        for (int y = 0; y < initialMap.size(); y++) {
            String line = initialMap.get(y);
            for (int x = 0; x < line.length(); x++) {
                map.put(new Position(x, y), charMapper.apply(line.charAt(x)));
            }
        }

        return new TaskMap<>(map);
    }

    public String asString(BiFunction<Position, T, String> valueStringMapper) {
        StringBuilder sb = new StringBuilder();
        for (int y = 0; y <= height; y++) {
            for (int x = 0; x <= width; x++) {
                Position position = new Position(x, y);
                T value = internalRepresentation.getOrDefault(position, null);
                sb.append(Optional.of(valueStringMapper.apply(position, value)).orElse(" "));
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public T at(Position p, T orElse) {
        return internalRepresentation.getOrDefault(p, orElse);
    }

    public T atRelativeFrom(Position p, Direction d, int steps, T orElse) {
        return this.at(new Position(p.x() + d.x() * steps, p.y() + d.y() * steps), orElse);
    }

    public List<T> checkSurroundings(Position p, T orElse) {
        if (!isWithinBounds(p)) {
            return List.of();
        }
        final var results = new ArrayList<T>();
        for (Direction d : Direction.allDirections()) {
            results.add(this.atRelativeFrom(p, d, 1, orElse));
        }
        return results;
    }

    public boolean isWithinBounds(Position p) {
        return p.x() >= 0 && p.x() <= width && p.y() >= 0 && p.y() <= height;
    }

    public TaskMap<T> set(Position obstructionPosition, T c) {
        internalRepresentation.put(obstructionPosition, c);
        return new TaskMap<>(internalRepresentation);
    }

    @Override
    @NonNull
    public Iterator<Pair<Position, T>> iterator() {
        return internalRepresentation.entrySet().stream().map(e -> new Pair<>(e.getKey(), e.getValue())).iterator();
    }
}
