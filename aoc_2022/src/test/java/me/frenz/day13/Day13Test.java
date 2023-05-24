package me.frenz.day13;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;


class Day13Test {

    private Day13 dut;

    @BeforeEach
    void setUp() {
        String EXAMPLE_INPUT = """
                [1,1,3,1,1]
                [1,1,5,1,1]
                                
                [[1],[2,3,4]]
                [[1],4]
                                
                [9]
                [[8,7,6]]
                                
                [[4,4],4,4]
                [[4,4],4,4,4]
                                
                [7,7,7,7]
                [7,7,7]
                                
                []
                [3]
                                
                [[[]]]
                [[]]
                                
                [1,[2,[3,[4,[5,6,7]]]],8,9]
                [1,[2,[3,[4,[5,6,0]]]],8,9]
                """;
        dut = new Day13(EXAMPLE_INPUT.lines().collect(Collectors.toList()));
    }

    @Test
    void testParsePacket_singleList() {
        final PacketParser parser = new PacketParser();
        final ContainerPacket result = new ContainerPacket();
        parser.parsePacket(result, "[1,1,3,1,1]");
        assertNotNull(result);
        assertEquals(new ContainerPacket(List.of(
                new ContainerPacket(List.of(
                        new IntPacket(1),
                        new IntPacket(1),
                        new IntPacket(3),
                        new IntPacket(1),
                        new IntPacket(1)
                )
        ))), result);
    }

    @Test
    void testParsePacket_innerLists() {
        final PacketParser parser = new PacketParser();
        final ContainerPacket result = new ContainerPacket();
        parser.parsePacket(result,"[[1],[2,3,4]]");
        assertNotNull(result);
        assertEquals(new ContainerPacket(List.of(
                new ContainerPacket(List.of(
                        new ContainerPacket(
                                List.of(new IntPacket(1))
                        ),
                        new ContainerPacket(
                                List.of(new IntPacket(2), new IntPacket(3), new IntPacket(4))
                        )
                )
        ))), result);
    }

    @Test
    void testParsePacket_doubleList() {
        final PacketParser parser = new PacketParser();
        final ContainerPacket result = new ContainerPacket();
        parser.parsePacket(result,"[[8,7,6]]");
        assertNotNull(result);
        assertEquals(new ContainerPacket(List.of(
                new ContainerPacket(List.of(
                        new ContainerPacket(
                                List.of(
                                        new IntPacket(8),
                                        new IntPacket(7),
                                        new IntPacket(6)
                                )
                        )
                )
        ))), result);
    }

    @Test
    void part1() {
        assertEquals(13, dut.part1());
    }

    @Test
    void part2() {
        assertEquals(-1, dut.part2());
    }
}
