package me.frenz.day13;

import me.frenz.Day;
import me.frenz.Pair;

import java.util.ArrayList;
import java.util.List;


public class Day13 extends Day<Integer, Integer> {

    private final PacketParser packetParser = new PacketParser();

    public Day13(final List<String> input) {
        super(input.stream().filter(it -> !it.isEmpty()).toList());
    }

    @Override
    protected Integer part1() {
        final List<Pair<PacketValue>> parseInput = parseInput();
        int sum = 0;
        int i = 0;
        boolean done = false;
        while (!done) {
            final Pair<PacketValue> packets = parseInput.get(i);
            final PacketValue left = packets.left();
            final PacketValue right = packets.right();

            i++;
            if (left.compareTo(right) > 0) {
                sum += i;
            }
            done = i >= parseInput.size();
        }
        return sum;
    }

    @Override
    protected Integer part2() {
        return -1;
    }

    List<Pair<PacketValue>> parseInput() {
        assert input.size() % 2 == 0 : "Input lines not divisible by two! Got %s lines".formatted(input.size());
        final List<Pair<PacketValue>> packetPairs = new ArrayList<>();

        for (int i = 0; i < input.size(); i += 2) {
            final ContainerPacket left = new ContainerPacket();
            final ContainerPacket right = new ContainerPacket();
            packetParser.parsePacket(left, input.get(i));
            packetParser.parsePacket(right, input.get(i + 1));

            packetPairs.add(new Pair<>(left.unwrap(), right.unwrap()));
        }

        return packetPairs;
    }


}
