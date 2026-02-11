package hse.java;

public interface IntExpression {
    String toString();
    int eval();
}

class Const implements IntExpression {
    private final int value;
    Const(int value) {this.value = value;}
    public int eval() {return value;}
    public String toString() {return "" + value;}
}

class Binary implements IntExpression {
    private final IntExpression left, right;
    private final char op;

    Binary(IntExpression left, IntExpression right, char op) {
        this.left = left;
        this.right = right;
        this.op = op;
    }

    public int eval() {
        int a = left.eval(), b = right.eval();
        if (op == '+') return a + b;
        if (op == '-') return a - b;
        if (op == '*') return a * b;
        if (op == '/') return a / b;
        throw new IllegalStateException();
    }

    public String toString() {return "(" + left + op + right + ")";}
}

class Sum extends Binary {
    Sum(IntExpression left, IntExpression right) {
        super(left, right, '+');
    }
    Sum(int left, int right) {this(new Const(left), new Const(right));}
}

class Sub extends Binary {
    Sub(IntExpression left, IntExpression right) {
        super(left, right, '-');
    }
    Sub(int left, int right) {this(new Const(left), new Const(right));}
}

class Mul extends Binary {
    Mul(IntExpression left, IntExpression right) {
        super(left, right, '*');
    }
    Mul(int left, int right) {this(new Const(left), new Const(right));}
}

class Div extends Binary {
    Div(IntExpression left, IntExpression right) {
        super(left, right, '/');
    }
    Div(int left, int right) {this(new Const(left), new Const(right));}
}

