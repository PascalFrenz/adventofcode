package me.frenz.day13

import me.frenz.Day
import me.frenz.Pair
import java.util.*
import kotlin.collections.ArrayList

class Day13(input: List<String>) : Day<Int?, Int?>(input.filter(String::isNotBlank)) {
    private val packetParser = PacketParser()

    val parsedInput: List<Pair<PacketValue>>
        get() = parseInput()

    public override fun part1(): Int {
        val parsedInput = parsedInput
        val sum: MutableList<Int> = ArrayList(parsedInput.size)
        for (i in parsedInput.indices) {
            val packets = parsedInput[i]
            if (compare(packets.left, packets.right) > 0) {
                sum.add(i + 1)
            }
        }
        return Optional.ofNullable(sum.reduceOrNull(Integer::sum)).orElse(0)
    }

    public override fun part2(): Int {
        return -1
    }

    private fun parseInput(): List<Pair<PacketValue>> {
        assert(input.size % 2 == 0) { "Input lines not divisible by two! Got %s lines".format(input.size) }
        return input.windowed(2, step = 2)
            .map { Pair(packetParser.parsePacket(it[0]), packetParser.parsePacket(it[1])) }
            .toList()
    }

    fun compare(left: PacketValue, right: PacketValue): Int {
        return when {
            left is IntPacket && right is IntPacket ->
                right.get().compareTo(left.get())
            left is IntPacket && right is ContainerPacket ->
                compare(ContainerPacket(listOf(left)), right)
            left is ContainerPacket && right is IntPacket ->
                compare(left, ContainerPacket(listOf(right)))
            left is ContainerPacket && right is ContainerPacket ->
                compareContainerPackets(left, right)
            else -> throw IllegalArgumentException("This should never happen")
        }
    }

    private fun compareContainerPackets(left: ContainerPacket, right: ContainerPacket): Int {
        val lPackets = left.get().size
        val rPackets = right.get().size
        var idx = 0
        while (idx < lPackets && idx < rPackets) {
            when (compare(left.get()[idx]!!, right.get()[idx]!!)) {
                1 -> return 1
                -1 -> return -1
                else -> idx++
            }
        }
        return when {
            idx == lPackets && idx != rPackets -> 1
            idx == rPackets && idx != lPackets -> -1
            else -> 0
        }
    }
}
