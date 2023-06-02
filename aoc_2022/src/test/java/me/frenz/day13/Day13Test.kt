package me.frenz.day13;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;


class Day13Test {

    private Day13 dut;

    private static IntPacket intPacket(int value) {
        return new IntPacket(value);
    }

    private static ContainerPacket containerPacket(final PacketValue ...packets) {
        return new ContainerPacket(
                List.of(packets)
        );
    }

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
        assertEquals(containerPacket(
                containerPacket(
                        intPacket(1),
                        intPacket(1),
                        intPacket(3),
                        intPacket(1),
                        intPacket(1)
                )
        ), result);
    }

    @Test
    void testParsePacket_innerLists() {
        final PacketParser parser = new PacketParser();
        final ContainerPacket result = new ContainerPacket();
        parser.parsePacket(result,"[[1],[2,3,4]]");
        assertNotNull(result);
        assertEquals(containerPacket(
                containerPacket(
                        containerPacket(intPacket(1)),
                        containerPacket(intPacket(2), intPacket(3), intPacket(4))
                )
        ), result);
    }

    @Test
    void testParsePacket_doubleList() {
        final PacketParser parser = new PacketParser();
        final ContainerPacket result = new ContainerPacket();
        parser.parsePacket(result,"[[8,7,6]]");
        assertNotNull(result);
        assertEquals(containerPacket(
                containerPacket(
                        containerPacket(
                                intPacket(8),
                                intPacket(7),
                                intPacket(6)
                        )
                )
        ), result);
    }

    @Test
    void givenTwoInts_ifLeftIsSmaller_shouldReturnOne() {
        final int result = dut.compare(intPacket(0), intPacket(1));
        assertEquals(1, result);
    }

    @Test
    void givenTwoInts_ifRightIsSmaller_shouldReturnMinusOne() {
        final int result = dut.compare(intPacket(1), intPacket(0));
        assertEquals(-1, result);
    }

    @Test
    void givenOneIntAndOneList_whenComparing_shouldConvertToListAndCompareInts_RightOrder() {
        final int result = dut.compare(intPacket(0), containerPacket(intPacket(1)));
        assertEquals(1, result);
    }

    @Test
    void givenOneIntAndOneList_whenComparing_shouldConvertToListAndCompareInts_WrongOrder() {
        final int result = dut.compare(containerPacket(intPacket(1)), intPacket(0));
        assertEquals(-1, result);
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
