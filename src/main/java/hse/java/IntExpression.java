package hse.java;

public interface IntExpression {
    String toString();
    int eval();
}


class Const implements IntExpression {
    private final int value;
    public Const(int _value) {
        this.value = _value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    @Override
    public int eval() {
        return value;
    }
}

abstract class BinOp implements IntExpression {
    final IntExpression left, right;
    public BinOp(IntExpression _left, IntExpression _right) {
        this.left = _left;
        this.right = _right;
    }

    @Override
    public String toString() {
        return "(" + left.toString() + getSymb() + right.toString() + ")";
    }

    abstract String getSymb();
}

class Sum extends BinOp {
    public Sum(IntExpression _left, IntExpression _right) {
        super(_left, _right);
    }

    @Override
    public int eval() {
        return left.eval() + right.eval();
    }

    @Override
    String getSymb() {
        return "+";
    }
}

class Sub extends BinOp {
    public Sub(IntExpression _left, IntExpression _right) {
        super(_left, _right);
    }

    @Override
    public int eval() {
        return left.eval() - right.eval();
    }

    @Override
    String getSymb() {
        return "-";
    }
}

class Mul extends BinOp {
    public Mul(IntExpression _left, IntExpression _right) {
        super(_left, _right);
    }

    @Override
    public int eval() {
        return left.eval() * right.eval();
    }

    @Override
    String getSymb() {
        return "*";
    }
}

class Div extends BinOp {
    public Div(IntExpression _left, IntExpression _right) {
        super(_left, _right);
    }

    @Override
    public int eval() {
        return left.eval() / right.eval();
    }

    @Override
    String getSymb() {
        return "/";
    }
}