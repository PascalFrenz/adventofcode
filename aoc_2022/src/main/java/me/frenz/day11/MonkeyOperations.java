package me.frenz.day11;

import java.math.BigInteger;


sealed abstract class MonkeyOperation permits MultMonkeyOperation, AddMonkeyOperation {
    protected final BigInteger op1;
    protected final BigInteger op2;

    MonkeyOperation(BigInteger op1, BigInteger op2) {
        this.op1 = op1;
        this.op2 = op2;
    }

    abstract BigInteger execute();
}


final class MultMonkeyOperation extends MonkeyOperation {

    MultMonkeyOperation(BigInteger op1, BigInteger op2) {
        super(op1, op2);
    }

    @Override
    BigInteger execute() {
        return this.op1.multiply(this.op2);
    }
}


final class AddMonkeyOperation extends MonkeyOperation {

    AddMonkeyOperation(BigInteger op1, BigInteger op2) {
        super(op1, op2);
    }

    @Override
    BigInteger execute() {
        return this.op1.add(this.op2);
    }
}
