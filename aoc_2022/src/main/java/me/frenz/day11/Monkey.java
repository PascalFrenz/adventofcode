package me.frenz.day11;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static lombok.AccessLevel.PACKAGE;


@EqualsAndHashCode
class Monkey {

    private static final int STARTING_ITEMS_IDX = 1;
    private static final int OPERATION_IDX = 2;
    private static final int TEST_IDX = 3;

    @Getter(PACKAGE) private final MonkeyOperationFactory operationFactory;
    @Getter(PACKAGE) private final MonkeyTest test;
    @Getter(PACKAGE) private final int positiveTargetMonkeyIdx;
    @Getter(PACKAGE) private final int negativeTargetMonkeyIdx;

    @Getter(PACKAGE) private long inspections = 0;
    private final Deque<BigInteger> items = new LinkedList<>();

    Monkey(final List<Integer> startingItems, MonkeyOperationFactory operationFactory, MonkeyTest monkeyTest, int positiveTargetMonkeyIdx, int negativeTargetMonkeyIdx) {
        this.operationFactory = operationFactory;
        this.test = monkeyTest;
        this.positiveTargetMonkeyIdx = positiveTargetMonkeyIdx;
        this.negativeTargetMonkeyIdx = negativeTargetMonkeyIdx;
        this.items.addAll(startingItems.stream().map(BigInteger::valueOf).map(operationFactory::create).map(MonkeyOperation::execute).toList());
    }

    static Monkey parse(final List<String> monkeyDef) {
        final String[] startingItemsArr = monkeyDef.get(STARTING_ITEMS_IDX).trim().split("Starting items: ")[1].split(",");
        final List<Integer> startingItems = Arrays.stream(startingItemsArr).map(String::trim).mapToInt(Integer::parseInt).boxed().collect(Collectors.toList());

        final String[] ops = monkeyDef.get(OPERATION_IDX).trim().split("Operation: new = ")[1].split(" ");
        final MonkeyOperationFactory operationFactory = new MonkeyOperationFactory(ops[1], ops[0], ops[2]);

        final String[] testOps = monkeyDef.get(TEST_IDX).trim().split("Test: ")[1].split(" ");
        final MonkeyTest monkeyTest = new MonkeyTest(testOps[0], Integer.parseInt(testOps[2]));

        final int trueMonkeyIdx = Integer.parseInt(monkeyDef.get(4).trim().split("If true: throw to monkey ")[1]);
        final int falseMonkeyIdx = Integer.parseInt(monkeyDef.get(5).trim().split("If false: throw to monkey ")[1]);

        return new Monkey(startingItems, operationFactory, monkeyTest, trueMonkeyIdx, falseMonkeyIdx);
    }

    List<BigInteger> getItems() {
        return new ArrayList<>(items);
    }

    MonkeyItem throwItem(final Function<BigInteger, BigInteger> worryFn) {
        this.inspections++;
        final BigInteger worryLevel = worryFn.apply(items.removeFirst());
        final int nextMonkey = test.test(worryLevel) ? positiveTargetMonkeyIdx : negativeTargetMonkeyIdx;

        return new MonkeyItem(nextMonkey, worryLevel);
    }

    void receive(final BigInteger item) {
        this.items.addLast(operationFactory.create(item).execute());
    }

    boolean hasItems() {
        return !items.isEmpty();
    }

}
