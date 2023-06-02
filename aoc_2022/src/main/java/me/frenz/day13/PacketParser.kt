package me.frenz.day13

import java.util.*

internal class PacketParser {

    fun parsePacket(packet: String): PacketValue {
        val stack = Stack<PacketValue>()
        stack.push(ContainerPacket())

        var dupPacket = packet
        while (dupPacket.isNotEmpty()) {
            when (dupPacket[0]) {
                '[' -> {
                    stack.push(ContainerPacket())
                    dupPacket = dupPacket.substring(1)
                }
                ']' -> {
                    val topItem = stack.pop()
                    (stack.peek() as ContainerPacket).add(topItem)
                    dupPacket = dupPacket.substring(1)
                }
                ',' -> {
                    dupPacket = dupPacket.substring(1)
                }
                else -> {
                    // it's a digit
                    val digString = StringBuilder()
                    var i = 0
                    while (dupPacket[i].isDigit()) {
                        digString.append(dupPacket[i++])
                    }
                    (stack.peek() as ContainerPacket).add(IntPacket(digString.toString().toInt()))
                    dupPacket = dupPacket.substring(digString.length)
                }
            }
        }
        return (stack.pop() as ContainerPacket).unwrap()
    }
}
