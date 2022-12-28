package me.frenz.day11;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;


class Day11Test {

    private Day11 dut;

    @BeforeEach
    void setUp() {
        String EXAMPLE_INPUT = """
                Monkey 0:
                  Starting items: 79, 98
                  Operation: new = old * 19
                  Test: divisible by 23
                    If true: throw to monkey 2
                    If false: throw to monkey 3
                                
                Monkey 1:
                  Starting items: 54, 65, 75, 74
                  Operation: new = old + 6
                  Test: divisible by 19
                    If true: throw to monkey 2
                    If false: throw to monkey 0
                                
                Monkey 2:
                  Starting items: 79, 60, 97
                  Operation: new = old * old
                  Test: divisible by 13
                    If true: throw to monkey 1
                    If false: throw to monkey 3
                                
                Monkey 3:
                  Starting items: 74
                  Operation: new = old + 3
                  Test: divisible by 17
                    If true: throw to monkey 0
                    If false: throw to monkey 1
                """;
        dut = new Day11(EXAMPLE_INPUT.lines().collect(Collectors.toList()));
    }

    @Test
    void part1() {
        assertEquals(10605, dut.part1());
    }

    @Test
    void part2() {
        assertTimeoutPreemptively(Duration.of(2, ChronoUnit.SECONDS), () -> assertEquals(2713310158L, dut.part2()));
    }

    @Test
    void givenSingleInput_shouldParseMonkey() {
        final String singleMonkey = """
                Monkey 0:
                  Starting items: 79, 98
                  Operation: new = old * 19
                  Test: divisible by 23
                    If true: throw to monkey 2
                    If false: throw to monkey 3
                """;

        final Monkey monkey = Monkey.parse(singleMonkey.lines().collect(Collectors.toList()));
        assertNotNull(monkey);
        assertEquals(List.of(BigInteger.valueOf(79).multiply(BigInteger.valueOf(19)), BigInteger.valueOf(98).multiply(BigInteger.valueOf(19))), monkey.getItems());
        assertEquals(new MonkeyOperationFactory("*", "old", "19"), monkey.getOperationFactory());
        assertEquals(new MonkeyTest("divisible", 23), monkey.getTest());
        assertEquals(2, monkey.getPositiveTargetMonkeyIdx());
        assertEquals(3, monkey.getNegativeTargetMonkeyIdx());
    }

    @Test
    void givenMultipleMonkeyInputs_shouldParseToList() {
        assertEquals(List.of(
                new Monkey(
                        List.of(79, 98),
                        new MonkeyOperationFactory("*", "old", "19"),
                        new MonkeyTest("divisible", 23),
                        2,
                        3
                ),
                new Monkey(
                        List.of(54, 65, 75, 74),
                        new MonkeyOperationFactory("+", "old", "6"),
                        new MonkeyTest("divisible", 19),
                        2,
                        0
                ),
                new Monkey(
                        List.of(79, 60, 97),
                        new MonkeyOperationFactory("*", "old", "old"),
                        new MonkeyTest("divisible", 13),
                        1,
                        3
                ),
                new Monkey(
                        List.of(74),
                        new MonkeyOperationFactory("+", "old", "3"),
                        new MonkeyTest("divisible", 17),
                        0,
                        1
                )
        ), dut.parseMonkeys());
    }

    @Test
    void givenMonkey_shouldCorrectlyCalculateNextMonkey() {
        final Monkey monkey = new Monkey(
                List.of(79, 98),
                new MonkeyOperationFactory("*", "old", "19"),
                new MonkeyTest("divisible", 23),
                2,
                3
        );

        assertEquals(new MonkeyItem(3, BigInteger.valueOf(500)), monkey.throwItem(worry -> worry.divide(BigInteger.valueOf(3))));
        assertEquals(new MonkeyItem(3, BigInteger.valueOf(620)), monkey.throwItem(worry -> worry.divide(BigInteger.valueOf(3))));
    }

    @Test
    void givenMonkeyItem_shouldBeAbleToReceiveItem() {
        final Monkey monkey = new Monkey(
                List.of(79, 98),
                new MonkeyOperationFactory("*", "old", "19"),
                new MonkeyTest("divisible", 23),
                2,
                3
        );

        monkey.receive(BigInteger.valueOf(500));
        assertEquals(List.of(
                BigInteger.valueOf(79).multiply(BigInteger.valueOf(19)),
                BigInteger.valueOf(98).multiply(BigInteger.valueOf(19)),
                BigInteger.valueOf(500).multiply(BigInteger.valueOf(19))
        ), monkey.getItems());
    }
}
