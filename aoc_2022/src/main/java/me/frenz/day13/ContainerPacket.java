package me.frenz.day13;

import java.util.ArrayList;
import java.util.List;

class ContainerPacket extends PacketValue implements Comparable<PacketValue> {
    private final List<PacketValue> packets;

    ContainerPacket() {
        this(new ArrayList<>());
    }

    ContainerPacket(List<PacketValue> packets) {
        this.packets = packets;
    }

    ContainerPacket unwrap() {
        assert packets.size() == 1;
        if (packets.get(0) instanceof ContainerPacket inner) {
            return inner;
        }
        throw new IllegalStateException("Did not find inner container packet to unwrap");
    }

    @Override
    void add(PacketValue child) {
        packets.add(child);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ContainerPacket that)) return false;

        return packets.equals(that.packets);
    }

    @Override
    public int hashCode() {
        return packets.hashCode();
    }

    @Override
    public String toString() {
        return packets.toString();
    }

    @Override
    public int compareTo(PacketValue o) {
        if (o instanceof ContainerPacket other) {
            int compareResult = 0;
            for (int i = 0; compareResult == 0 && i < this.packets.size(); i++) {
                if (other.packets.size() <= i) {
                    compareResult = -1;
                } else {
                    final PacketValue l = this.packets.get(i);
                    final PacketValue r = other.packets.get(i);
                    compareResult = l.compareTo(r);
                }
            }
            return compareResult == 0 ? 1 : compareResult;
        } else if (o instanceof IntPacket i) {
            return this.compareTo(new ContainerPacket(List.of(i)));
        } else {
            throw new UnsupportedOperationException("Cannot compare with type: " + o.getClass().getName());
        }
    }
}
