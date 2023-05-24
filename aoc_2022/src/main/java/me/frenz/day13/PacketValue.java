package me.frenz.day13;

abstract class PacketValue implements Comparable<PacketValue> {
    abstract void add(PacketValue child);
}
