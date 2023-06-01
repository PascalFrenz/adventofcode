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
        final List<Integer> sum = new ArrayList<>(parseInput.size());
        for (int i = 0; i < parseInput.size(); i++) {
            final Pair<PacketValue> packets = parseInput.get(i);
            final PacketValue left = packets.left(), right = packets.right();

            if (compare(left, right) > 0) {
                sum.add(i + 1);
            }
        }

        return sum.stream().reduce(Integer::sum).orElse(-1);
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

    int compare(PacketValue left, PacketValue right) {
        if (left instanceof IntPacket li && right instanceof IntPacket ri) {
            return Integer.compare(ri.get(), li.get());
        } else if (left instanceof IntPacket li && right instanceof ContainerPacket rc) {
            return compare(new ContainerPacket(List.of(li)), rc);
        } else if (left instanceof ContainerPacket lc && right instanceof IntPacket ri) {
            return compare(lc, new ContainerPacket(List.of(ri)));
        } else if (left instanceof ContainerPacket lc && right instanceof ContainerPacket rc) {
            int areEqual = 0; // 0 means continue checking
            final int leftSize = lc.get().size();
            final int rightSize = rc.get().size();
            for (int i = 0; i < leftSize; i++) {
                if (i >= rightSize) {
                    areEqual = -1;
                    break;
                }
                areEqual = compare(lc.get().get(i), rc.get().get(i));
            }
            return rightSize > leftSize ? 1 : areEqual;
        } else {
            throw new IllegalArgumentException("This should never happen");
        }
    }
}
