package me.frenz.day13;

import java.util.ArrayList;
import java.util.List;

class ContainerPacket extends PacketValue {
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

    public List<PacketValue> get() {
        return packets;
    }
}
